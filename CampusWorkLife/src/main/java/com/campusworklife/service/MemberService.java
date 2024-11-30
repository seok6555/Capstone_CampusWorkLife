package com.campusworklife.service;

import org.springframework.stereotype.Service;

import com.campusworklife.dto.LoginRequest;
import com.campusworklife.dto.LoginResponse;
import com.campusworklife.entity.Member2;
import com.campusworklife.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;  // final 키워드 추가
    private final AdminService adminService;
    
    public LoginResponse login(LoginRequest request) {
        System.out.println("MemberService login 메소드 진입");
        
        // 관리자 인증
        if (adminService.authenticateAdmin(request.getUsername(), request.getPw_hash())) {
            System.out.println("관리자 인증 성공");
            return new LoginResponse("관리자 로그인 성공", request.getUsername(), true); // 관리자 플래그 true
        }
        
         
        // 일반 사용자 인증
        Member2 member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    System.out.println("사용자를 찾을 수 없음 예외 발생");
                    return new RuntimeException("사용자를 찾을 수 없습니다.");
                });

        if (!request.getPw_hash().equals(member.getPw_hash())) {
            System.out.println("비밀번호 불일치 예외 발생");
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponse("로그인 성공", member.getUsername(), false);
    }
}