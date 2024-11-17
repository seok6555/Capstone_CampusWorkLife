package com.campusworklife.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campusworklife.entity.Workplace;

public interface WorkplaceRepository extends JpaRepository<Workplace, Integer> {
	
}