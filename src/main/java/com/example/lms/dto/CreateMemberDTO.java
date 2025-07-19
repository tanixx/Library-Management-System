package com.example.lms.dto;

import lombok.Data;

@Data
public class CreateMemberDTO {
    private String email;
    private String name;
    private String phone;
    private String studentId;
}
