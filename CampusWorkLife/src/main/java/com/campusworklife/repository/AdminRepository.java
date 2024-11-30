package com.campusworklife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusworklife.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
    boolean existsByAdminIdAndAdminPw(String adminId, String adminPw);
}
