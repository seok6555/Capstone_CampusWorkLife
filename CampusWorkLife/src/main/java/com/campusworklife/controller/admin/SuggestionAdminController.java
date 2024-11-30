package com.campusworklife.controller.admin;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campusworklife.entity.Suggestion;
import com.campusworklife.repository.SuggestionRepository;

@Controller
@RequestMapping("admin")
public class SuggestionAdminController {
    
    @Autowired 
    SuggestionRepository suggestionRepository;

    ModelMapper modelMapper = new ModelMapper();

 // 건의사항 목록 페이지
    @GetMapping("suggestionAdminPage")
    public String suggestionAdminPage(Model model) {
        List<Suggestion> progressSuggestions = suggestionRepository.findByState("progress");
        model.addAttribute("suggestions", progressSuggestions);
        return "admin/suggestionAdminPage"; // admin 폴더에 위치한 HTML 파일
    }
}
