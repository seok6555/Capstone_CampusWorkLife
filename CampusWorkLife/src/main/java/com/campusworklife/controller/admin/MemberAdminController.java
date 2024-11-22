package com.campusworklife.controller.admin;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campusworklife.dto.Member;
import com.campusworklife.entity.Officelist;
import com.campusworklife.mapper.MemberMapper;
import com.campusworklife.repository.OfficelistRepository;

@Controller
@RequestMapping("admin")
public class MemberAdminController {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private OfficelistRepository officelistRepository;

	@GetMapping("memAdminPage")
	public String memAdminPage(Model model) {
		//String pageTitle = "회원관리";
		List<Member> members = memberMapper.findAll();
		List<Officelist> offices = officelistRepository.findAll();
		model.addAttribute("members", members);
		model.addAttribute("offices", offices);
		return "admin/memAdminPage";
	}
	
	@PostMapping(value="edit", params="cmd=delete")
	public String delete(@RequestParam("username") String username, Model model) {
		try {
			memberMapper.deleteById(username);
			return "redirect:memAdminPage";
		}
		catch(Exception ex) {
			model.addAttribute("error", "회원 삭제에 실패했습니다.");
			return "admin/memAdminPage";
		}
	}
}

