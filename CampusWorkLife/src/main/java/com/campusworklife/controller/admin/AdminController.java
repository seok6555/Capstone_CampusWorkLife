package com.campusworklife.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.campusworklife.dto.Member;
import com.campusworklife.mapper.MemberMapper;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@GetMapping("memAdminPage")
	public String memAdminPage(Model model) {
		String pageTitle = "회원관리";
		model.addAttribute("message", pageTitle);
		return "admin/memAdminPage";
	}
	
	@GetMapping("suggestionAdminPage")
	public String suggestionAdminPage(Model model) {
		String pageTitle = "게시판 관리";
		model.addAttribute("message", pageTitle);
		return "admin/suggestionAdminPage";
	}
	
	@GetMapping("pageAdminPage")
	public String pageAdminPage(Model model) {
		String pageTitle = "페이지 관리";
		model.addAttribute("message", pageTitle);
		return "admin/pageAdminPage";
	}
}
