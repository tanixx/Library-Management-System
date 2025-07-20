package com.example.lms.dto;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookRequestDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String category;
}
