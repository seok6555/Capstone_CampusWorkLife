package com.campusworklife.controller.admin;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campusworklife.entity.Suggestion;
import com.campusworklife.repository.MemberRepository;
import com.campusworklife.repository.SuggestionRepository;

@Controller
@RequestMapping("admin")
public class SuggestionAdminController {
    
    @Autowired 
    SuggestionRepository suggestionRepository;
    
    @Autowired 
    MemberRepository memberRepository;
    
    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("suggestionAdminPage")
    public String suggestionAdminPage(Model model) {
        //String pageTitle = "게시판 관리";
        // model.addAttribute("message", pageTitle);
        
        // 'progress' 상태의 건의사항만 가져오기
        List<Suggestion> progressSuggestions = suggestionRepository.findByState("progress");
        
        model.addAttribute("suggestions", progressSuggestions);
        return "admin/suggestionAdminPage";
    }

}
