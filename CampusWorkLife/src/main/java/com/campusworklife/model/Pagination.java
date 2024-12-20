package com.campusworklife.model;

import lombok.Data;

@Data
public class Pagination {
	int pg=1;//현재 페이지 번호
	int sz=15;//페이지당 레코드 수
	int recordCount;//전체 레코드 수
	
	public int getFirstRecordIndex() {
		return (pg-1)*sz;
	}

	public String getQuersyString() {
		return String.format("pg=%d&sz=%d", pg, sz);
	}
}
