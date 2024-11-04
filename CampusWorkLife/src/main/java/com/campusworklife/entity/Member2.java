package com.campusworklife.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="member")
public class Member2 {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;//아이디
	  
	  private String pw_hash;//비번
	  private String username;//이름
	  private String email;//이메일
	  private boolean emailVerified; // 이메일 인증 여부
	  
	  

}
