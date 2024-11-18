package com.campusworklife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusworklife.entity.Officelist;

public interface OfficelistRepository extends JpaRepository<Officelist, String> {
    
}

