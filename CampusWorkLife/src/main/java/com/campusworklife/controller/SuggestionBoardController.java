package com.campusworklife.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.campusworklife.entity.Member2;
import com.campusworklife.entity.Suggestion;
import com.campusworklife.model.Pagination;
import com.campusworklife.repository.MemberRepository;
import com.campusworklife.repository.SuggestionRepository;

import lombok.extern.slf4j.Slf4j;


/*
 건의 게시판(마이페이지x)
 */
@Controller
@RequestMapping("suggest")
@Slf4j
public class SuggestionBoardController {
	/*
	 글쓰기, 수정, 삭제도 필요함,건의게시판 작성시간도 필요함->최신순 정렬 가능..근데 사용자 뷰어에선 굳이..
	 검색기능 추가로 필요..
	 
	 
	 
	 */
	
	@Autowired SuggestionRepository suggestionRepository;
	@Autowired MemberRepository memberRepository;
    ModelMapper modelMapper = new ModelMapper();
    
    
    //게시판 목록+검색창
    @GetMapping("list")
    public String list(
    		@RequestParam(value="searchType",required=false) String searchType,
    		@RequestParam(value="searchKeyword",required=false)String searchKeyword,
    		Model model,Pagination pagination) {
    	
    	pagination.setSz(10);
    	
    	Page<Suggestion> page;//조건문 밖에서 page변수를 선언한다.
    	//검색유형에 따라 적절한 쿼리 실행
    	//제목
    	   if("title".equals(searchType) && searchKeyword != null) {
    	        page = suggestionRepository.findByTitleContaining(searchKeyword,
    	                PageRequest.of(pagination.getPg() - 1, pagination.getSz(), Sort.Direction.DESC, "id"));
    	    } else if("username".equals(searchType) && searchKeyword != null) {
    	        page = suggestionRepository.findByMemberUsernameContaining(searchKeyword,
    	                PageRequest.of(pagination.getPg() - 1, pagination.getSz(), Sort.Direction.DESC, "id"));
    	    } else {
    	        page = suggestionRepository.findAll(PageRequest.of(pagination.getPg() - 1, 
    	                pagination.getSz(), Sort.Direction.DESC, "id"));
    	    }
    	    
    	    pagination.setRecordCount((int)page.getTotalElements());
    	    
    	    model.addAttribute("suggestions", page.getContent());
    	    model.addAttribute("pagination", pagination);
    	    model.addAttribute("searchType", searchType);
    	    model.addAttribute("searchKeyword", searchKeyword);
    	    
    	    return "suggest/list";
    	
    }
    
    //게시판 글쓰기(사용자)
    
    @GetMapping("/create")
    public String createForm(Model model, Principal principal) {
    	 model.addAttribute("title", "");
    	    model.addAttribute("content", "");
    	    if (principal != null) {
    	        model.addAttribute("username", principal.getName());  // 로그인한 사용자 이름
    	    }
    	    return "suggest/create";
    }

    @PostMapping("/create")
    public String create(@RequestParam String title,
                        @RequestParam String content,
                        @RequestParam String username,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        
        log.info("받은 데이터 - 제목: {}, 내용: {}, 사용자명: {}", title, content, username);
        
        try {
            // 입력값 검증
            if (title.trim().isEmpty() || content.trim().isEmpty() || username.trim().isEmpty()) {
                throw new IllegalArgumentException("모든 필드를 입력해주세요.");
            }
            
            // 사용자 찾기 - 여기서 사용자를 찾지 못하면 로그를 남깁니다
            Optional<Member2> memberOptional = memberRepository.findByUsername(username);
            if (!memberOptional.isPresent()) {
                log.error("사용자를 찾을 수 없습니다: {}", username);
                throw new RuntimeException("존재하지 않는 사용자입니다: " + username);
            }
            Member2 member = memberOptional.get();
            
            // Suggestion 객체 생성 및 저장
            Suggestion suggestion = new Suggestion();
            suggestion.setTitle(title);
            suggestion.setContent(content);
            suggestion.setState("progress");
            suggestion.setCreated(LocalDateTime.now());
            suggestion.setMember2(member);
            
            log.info("저장 전 suggestion 객체: {}", suggestion);
            Suggestion saved = suggestionRepository.save(suggestion);
            log.info("저장된 suggestion ID: {}", saved.getId());
            
            redirectAttributes.addFlashAttribute("message", "건의사항이 성공적으로 등록되었습니다.");
            return "redirect:/suggest/list";
            
        } catch (Exception e) {
            log.error("건의사항 생성 중 오류 발생", e);
            model.addAttribute("title", title);
            model.addAttribute("content", content);
            model.addAttribute("username", username);
            model.addAttribute("error", e.getMessage());
            return "suggest/create";
        }
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model, Principal principal) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("건의사항을 찾을 수 없습니다."));
        
        boolean isAuthor = false;
        if (principal != null) {
            isAuthor = suggestion.getMember2().getUsername().equals(principal.getName());
        }
        
        model.addAttribute("suggestion", suggestion);
        model.addAttribute("isAuthor", isAuthor);
        return "suggest/detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Principal principal, RedirectAttributes redirectAttributes) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("건의사항을 찾을 수 없습니다."));
        
        // Check if the current user is the author
        if (!suggestion.getMember2().getUsername().equals(principal.getName())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        
        suggestionRepository.delete(suggestion);
        redirectAttributes.addFlashAttribute("message", "건의사항이 삭제되었습니다.");
        return "redirect:/suggest/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model, Principal principal) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("건의사항을 찾을 수 없습니다."));
        
        // Check if the current user is the author
        if (!suggestion.getMember2().getUsername().equals(principal.getName())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        
        model.addAttribute("suggestion", suggestion);
        return "suggest/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id,
                      @RequestParam String title,
                      @RequestParam String content,
                      Principal principal,
                      RedirectAttributes redirectAttributes) {
        
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("건의사항을 찾을 수 없습니다."));
        
        // Check if the current user is the author
        if (!suggestion.getMember2().getUsername().equals(principal.getName())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        
        suggestion.setTitle(title);
        suggestion.setContent(content);
        suggestionRepository.save(suggestion);
        
        redirectAttributes.addFlashAttribute("message", "건의사항이 수정되었습니다.");
        return "redirect:/suggest/detail/" + id;
    }
}

