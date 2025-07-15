package com.example.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    private LocalDateTime requestedDate = LocalDateTime.now();


    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")        // FK column in book_requests table
    private Member member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;
}
