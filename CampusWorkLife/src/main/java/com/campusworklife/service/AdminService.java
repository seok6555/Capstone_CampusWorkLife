package com.campusworklife.service;

import org.springframework.stereotype.Service;
import com.campusworklife.repository.AdminRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public boolean authenticateAdmin(String adminId, String adminPw) {
        return adminRepository.existsByAdminIdAndAdminPw(adminId, adminPw);
    }
}
