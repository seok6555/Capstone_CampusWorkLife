package com.campusworklife.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campusworklife.dto.LoginRequest;
import com.campusworklife.entity.Member2;


import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/member")
public class MemberController {
	
	/*
	@Autowired
    private MemberService memberService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            Member2 member = memberService.login(loginRequest.getUsername(), loginRequest.getPassword());
            
            // Store user info in session
            session.setAttribute("userId", member.getId());
            session.setAttribute("username", member.getUsername());
            
            return ResponseEntity.ok()
                .body(Map.of(
                    "message", "Login successful",
                    "username", member.getUsername()
                ));
                
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok()
            .body(Map.of("message", "Logout successful"));
    }
	/*	
    @Autowired 
    private MemberRepository memberRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "member/login";
    }
/*
    @PostMapping("/login")
    public String loginProcess(@RequestParam String username, 
                             @RequestParam String password, 
                             Model model) {
    	System.out.println(username);
    	System.out.println(password);
        try {
            // Spring Security가 자동으로 처리하므로 별도의 로직이 필요 없습니다
        	System.out.println(username);
        	System.out.println(password);
            return "redirect:/suggest/list";
        } catch (Exception e) {
            model.addAttribute("error", "로그인에 실패했습니다.");
            return "suggest/list";
        }
    } */
  
    /*
    @PostMapping("/login")
    @ResponseBody
    public String encryptExistingPasswords() {
        List<Member2> members = memberRepository.findAll();
        for (Member2 member : members) {
            // 암호화되지 않은 비밀번호만 암호화
            if (!member.getPw_hash().startsWith("$2a$")) {
                member.setPw_hash(passwordEncoder.encode(member.getPw_hash()));
                memberRepository.save(member);
            }
        }
        return "비밀번호 암호화 완료";
    }
    */
}