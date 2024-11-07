package com.campusworklife.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Workplace {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;  // 근로지 번호


	    private String manager;  // 담당자
	    private Integer phone;  // 전화번호
	    
	    private LocalDate workStart;  // 근로 시작일
	    private LocalDate workEnd;    // 근로 종료일
	    
	    private LocalTime timeStart;  // 근로 시작시간
	    private LocalTime timeEnd;    // 근로 종료시간
	    
	    private Boolean dayMon;  // 근로요일(월)
	    private Boolean dayTue;  // 근로요일(화)
	    private Boolean dayWed;  // 근로요일(수)
	    private Boolean dayThu;  // 근로요일(목)
	    private Boolean dayFri;  // 근로요일(금)
	    private Boolean daySat;  // 근로요일(토)
	    private Boolean daySun;  // 근로요일(일)
	    
	    private Integer employeeCount;  // 모집인원
	    private String department;      // 선호학과
	    private Integer grade;          // 선호학년
	    private String summary;         // 직무 요약
	    private String notes;           // 특이사항
	    private String content;         // 내용
	    
	    @ManyToOne
	    @JoinColumn(name="office") // 부서명,외래키
	    Officelist officelist;
}
