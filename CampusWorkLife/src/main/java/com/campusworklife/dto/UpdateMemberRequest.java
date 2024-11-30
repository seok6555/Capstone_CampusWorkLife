package com.campusworklife.dto;

import lombok.Data;

@Data
public class UpdateMemberRequest {
	  private String currentUsername;
	    private String username;
	    private String currentEmail;
	    private String email;
	    private String password;
	    private String confirmPassword;
}
