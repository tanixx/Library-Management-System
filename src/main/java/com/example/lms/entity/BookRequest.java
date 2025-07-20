package com.example.lms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    @Column(unique = true, length = 20)
    private String isbn;
    private String category;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private LocalDateTime requestedDate = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member member;
}
