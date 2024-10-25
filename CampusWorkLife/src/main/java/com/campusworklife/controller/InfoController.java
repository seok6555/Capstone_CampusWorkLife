package com.campusworklife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campusworklife.dto.Product;

@Controller
@RequestMapping("info")
public class InfoController {
	
	@GetMapping("infoPage")
	public String infoPage(Model model) {
		String pageTitle = "근로지 부서명";
		model.addAttribute("message", pageTitle);
		return "info/infoPage";
	}
}
