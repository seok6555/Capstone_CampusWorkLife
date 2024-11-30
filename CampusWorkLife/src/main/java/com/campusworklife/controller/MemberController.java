package com.campusworklife.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.campusworklife.dto.LoginRequest;
import com.campusworklife.dto.LoginResponse;
import com.campusworklife.dto.UpdateMemberRequest;
import com.campusworklife.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	 private final MemberService memberService;
	    
	    @GetMapping("/login")
	    public String loginForm() {
	        return "member/login";  // login.html 뷰를 반환
	    }
	    @PostMapping("/login")
	    public String login(@RequestParam("username") String username,
	                       @RequestParam("pw_hash") String pw_hash,
	                       HttpSession session,
	                       HttpServletRequest request,
	                       RedirectAttributes redirectAttributes) {

	        System.out.println("==== 로그인 처리 시작 ====");
	        System.out.println("Session ID: " + session.getId());
	        System.out.println("요청 파라미터 - username: " + username);
	        System.out.println("요청 파라미터 - pw_hash: " + pw_hash);

	        try {
	            LoginResponse response = memberService.login(new LoginRequest(username, pw_hash));
	            // 디버깅을 위한 출력 추가
	            System.out.println("로그인 응답: " + response);
	            
	            session.setAttribute("username", response.getUsername());
	            session.setAttribute("loggedIn", true);
	            
                
	            // 저장된 returnUrl이 있는지 확인
	            String returnUrl = (String) session.getAttribute("returnUrl");
	            if (returnUrl != null) {
	                session.removeAttribute("returnUrl"); // 사용한 returnUrl 제거
	                return "redirect:" + returnUrl;
	            }
	            
	            System.out.println("로그인 성공 - 세션에 저장된 username: " + session.getAttribute("username"));
	            System.out.println("로그인 상태: " + session.getAttribute("loggedIn"));

	            return "redirect:/";
	        } catch (RuntimeException e) {
	            // 디버깅을 위한 출력 추가
	            System.out.println("캐치 블록 진입");
	            System.out.println("예외 내용: " + e.getMessage());
	            System.out.println("로그인 실패: " + e.getMessage());
	            
	            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	            return "redirect:/member/login";
	        }
	    }
	    //마이페이지
	    @GetMapping("/mypage")
	    public String mypage(HttpSession session, Model model) {
	        Boolean isLoggedIn = (Boolean) session.getAttribute("loggedIn");
	        if (isLoggedIn == null || !isLoggedIn) {
	            return "redirect:/member/login";
	        }
	        
	        
	        String username = (String) session.getAttribute("username");
	        
	        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin"); 
	        if(isAdmin == null) {
	        	isAdmin = false; //초기값
	        }

	        model.addAttribute("username", username);
	        
	        if(username.equals("admin1234")) {			//관리자 아이디로 로그인 여부 확인
	        	isAdmin = true;
	        }
	        
	        model.addAttribute("isAdmin", isAdmin);		//관리자 여부 전달
	        System.out.println("isAdmin " +isAdmin);	//확인용
	        return "member/mypage";
	    }

	    //로그아웃
	    @PostMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/";
	    }
	    
	    //수정하기
	    @PostMapping("/update")
	    public String updateMember(
	            @RequestParam("username") String newUsername,
	            @RequestParam("email") String newEmail,
	            @RequestParam(value = "password", required = false) String newPassword,
	            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
	            HttpSession session,
	            RedirectAttributes redirectAttributes) {
	        
	        String currentUsername = (String) session.getAttribute("username");
	        try {
	            UpdateMemberRequest request = new UpdateMemberRequest();
	            request.setUsername(newUsername);
	            request.setEmail(newEmail);
	            request.setPassword(newPassword);
	            request.setConfirmPassword(confirmPassword);
	            request.setCurrentUsername(currentUsername);

	            memberService.updateMember(request);
	            
	            // Update session with new username if changed
	            if (!currentUsername.equals(newUsername)) {
	                session.setAttribute("username", newUsername);
	            }
	            
	            redirectAttributes.addFlashAttribute("message", "회원정보가 성공적으로 수정되었습니다.");
	            return "redirect:/member/mypage";
	        } catch (RuntimeException e) {
	            redirectAttributes.addFlashAttribute("error", e.getMessage());
	            return "redirect:/member/mypage";
	        }
	    }
	//
}