package com.campusworklife.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Member {
    private int id;
    private String username;
    private String email;
    private LocalDateTime joinDate; 
    
    public String getFormattedJoinDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return joinDate.format(formatter);
    }
}


