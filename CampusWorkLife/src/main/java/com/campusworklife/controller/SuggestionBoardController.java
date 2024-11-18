package com.campusworklife.controller;

import java.time.LocalDateTime;

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
    public String createForm() {
        return "suggest/create";
    }

    @PostMapping("/create")
    public String create(@RequestParam String title,
                        @RequestParam String content,
                        @RequestParam String username
                       ) {
    	
    	log.info("Creating suggestion - Title: {}, Content: {}, Username: {}", 
                title, content, username);  // 로그 추가
        try {
            // 1. 새로운 Suggestion 객체 생성
        	   Member2 member = memberRepository.findByUsername(username)
                       .orElseThrow(() -> new RuntimeException("User not found: " + username));
        	   
               Suggestion suggestion = new Suggestion();
               suggestion.setTitle(title);
               suggestion.setContent(content);
               suggestion.setState("progress");
               suggestion.setCreated(LocalDateTime.now());
               suggestion.setMember2(member);
               
               Suggestion saved = suggestionRepository.save(suggestion);
               log.info("Suggestion saved with ID: {}", saved.getId());  // 로그 추가
               
               return "redirect:/suggest/list";
               
           } catch (Exception e) {
               log.error("Error creating suggestion", e);  // 상세 에러 로그
               return "suggest/create";
           }
    

    }
    //건의 상세 
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("건의사항을 찾을 수 없습니다."));
        
        model.addAttribute("suggestion", suggestion);
        return "suggest/detail";
    }
}

