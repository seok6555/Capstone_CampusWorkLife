package com.campusworklife.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    	Page<Suggestion> page;//조건문 밖에서 page변수를 선언한다.
    	//검색유형에 따라 적절한 쿼리 실행
    	//제목
    	if("title".equals(searchType)&&searchKeyword !=null) {
    		
    		 page=suggestionRepository.findByTitleContaining(searchKeyword,
    				PageRequest.of(pagination.getPg() - 1,
    		                pagination.getSz(), Sort.Direction.ASC, "id"));
    		
    	} 
    	//작성자
    	else if("username".equals(searchType)&&searchKeyword!=null) {
    		
    	 page=suggestionRepository.findByTitleContaining(searchKeyword,
    				PageRequest.of(pagination.getPg() - 1,
    		                pagination.getSz(), Sort.Direction.ASC, "id"));
    	}
    	else {
    		// 검색 유형이나 키워드가 없으면 모든 게시글 조회
    		 page = suggestionRepository.findAll(PageRequest.of(pagination.getPg() - 1, 
            		pagination.getSz(), Sort.Direction.ASC, "id"));
    	}
      
        pagination.setRecordCount((int)page.getTotalElements());

    	model.addAttribute("suggestions",page.getContent());
    	return "suggest/list";
    	
    }
    
    //게시판 글쓰기(사용자)
    /*
    @GetMapping("create")
    public String create(Model model, Pagination pagination) {
    	
    }
    */
    
    
    

}
