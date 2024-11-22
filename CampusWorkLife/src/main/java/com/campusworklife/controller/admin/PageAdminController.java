package com.campusworklife.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.campusworklife.dto.Member;
import com.campusworklife.mapper.MemberMapper;
import com.campusworklife.repository.OfficelistRepository;
import com.campusworklife.entity.Officelist;

@Controller
@RequestMapping("admin")
public class PageAdminController {

	@GetMapping("pageAdminPage")
	public String pageAdminPage(Model model) {
		String pageTitle = "페이지 관리";
		model.addAttribute("message", pageTitle);
		return "admin/pageAdminPage";
	}
}
