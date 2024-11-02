package com.campusworklife.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Member {
	String id;
	String username;
	String email;
	String workplace;
	LocalDate joinDate;
}
