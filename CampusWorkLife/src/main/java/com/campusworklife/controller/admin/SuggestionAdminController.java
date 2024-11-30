package com.campusworklife.controller.admin;

import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campusworklife.entity.Suggestion;
import com.campusworklife.repository.SuggestionRepository;

@Controller
@RequestMapping("admin")
public class SuggestionAdminController {

    @Autowired
    private SuggestionRepository suggestionRepository;  // SuggestionRepository 주입

 

    ModelMapper modelMapper = new ModelMapper();

    // 건의사항 목록 페이지
    @GetMapping("suggestionAdminPage")
    public String suggestionAdminPage(Model model) {
        List<Suggestion> progressSuggestions = suggestionRepository.findByState("progress");
        model.addAttribute("suggestions", progressSuggestions);
        return "admin/suggestionAdminPage"; // admin 폴더에 위치한 HTML 파일
    }

 // 상세 페이지
    @GetMapping("suggestionAdminDetail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model, Principal principal) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("건의사항을 찾을 수 없습니다."));
        
        boolean isAuthor = false;
        if (principal != null) {
            isAuthor = suggestion.getMember2().getUsername().equals(principal.getName());
        }
        
        model.addAttribute("suggestion", suggestion);
        model.addAttribute("isAuthor", isAuthor);
        return "suggest/detail"; // 해당 경로로 detail 페이지 렌더링
    }
    
}
