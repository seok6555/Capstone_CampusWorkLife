package com.campusworklife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="office_list")
public class Officelist {
	@Id
    @Column(name = "office_name")
    private String officeName;    // 부서명

}
