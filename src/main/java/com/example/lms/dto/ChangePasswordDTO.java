package com.example.lms.dto;

import lombok.Getter;
import lombok.Setter;

// ChangePasswordDTO.java
@Getter
@Setter
public class ChangePasswordDTO {
    private String currentPassword;
    private String newPassword;
}
