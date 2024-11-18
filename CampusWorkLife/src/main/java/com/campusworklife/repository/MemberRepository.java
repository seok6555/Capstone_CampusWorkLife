package com.campusworklife.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campusworklife.entity.Member2;

public interface MemberRepository extends JpaRepository<Member2,Integer> {



	
	 
    @Query("SELECT m FROM Member2 m WHERE m.username = :username")
    Optional<Member2> findByUsername(@Param("username") String username);
 

}
