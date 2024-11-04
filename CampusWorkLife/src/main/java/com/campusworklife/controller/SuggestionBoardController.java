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
	
	@Autowired SuggestionRepository suggestionRepository;
	@Autowired MemberRepository memberRepository;
    ModelMapper modelMapper = new ModelMapper();
    
    //게시판 목록
    @GetMapping("list")
    public String list(Model model,Pagination pagination) {
        Page<Suggestion> page = suggestionRepository.findAll(PageRequest.of(pagination.getPg() - 1,
                pagination.getSz(), Sort.Direction.ASC, "id"));
        pagination.setRecordCount((int)page.getTotalElements());

    	model.addAttribute("suggestions",page.getContent());
    	return "suggest/list";
    }
    
    
    

}
