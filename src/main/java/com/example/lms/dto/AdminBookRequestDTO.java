// AdminBookRequestDTO.java
package com.example.lms.dto;

import lombok.Data;

@Data
public class AdminBookRequestDTO {
    private Long id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private String requestedDate;
    private String memberId;
    private String status;
}
