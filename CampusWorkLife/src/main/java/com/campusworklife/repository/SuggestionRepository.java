package com.campusworklife.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.campusworklife.entity.Suggestion;

public interface SuggestionRepository extends JpaRepository<Suggestion,Integer> {

	

}