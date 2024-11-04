package com.campusworklife.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Suggestion {
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;               // 건의 아이디
	    
	    private Integer userId;           // 사람 아이디,외래키 
	    private String title;             // 제목
	    private String content;           // 내용
	    private  LocalDateTime created;        // 등록일
	    private String state;             // 상태 (progress or completed)
	

}
