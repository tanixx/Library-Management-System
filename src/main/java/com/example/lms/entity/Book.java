package com.example.lms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "books")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Column(unique = true, length = 20)
    private String isbn;

    private String category;

    private int totalCopies;
    private int availableCopies;
}
