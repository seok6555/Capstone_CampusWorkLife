package com.campusworklife.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class EditHistory {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;				// 수정 이력 번호
    
	private String username;		// 수정자 유저명

	private Timestamp edit_time;	// 수정 시간
    
	private String content;			// 수정 내용
    
    @ManyToOne
    @JoinColumn(name="workplace_id")// 근로지 아이디,외래키
    Workplace workplace;
}
