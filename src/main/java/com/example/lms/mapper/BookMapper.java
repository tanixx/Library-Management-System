package com.example.lms.mapper;

import com.example.lms.dto.BookDTO;
import com.example.lms.entity.Book;

public class BookMapper {

    public static BookDTO toDTO(Book entity) {
        return BookDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .isbn(entity.getIsbn())
                .category(entity.getCategory())
                .totalCopies(entity.getTotalCopies())
                .availableCopies(entity.getAvailableCopies())
                .build();
    }

    public static Book toEntity(BookDTO dto) {
        Book b = new Book();
        b.setTitle(dto.getTitle());
        b.setAuthor(dto.getAuthor());
        b.setIsbn(dto.getIsbn());
        b.setCategory(dto.getCategory());
        b.setTotalCopies(dto.getTotalCopies());
        b.setAvailableCopies(dto.getAvailableCopies());
        return b;
    }

    public static void copy(BookDTO dto, Book entity) {
        entity.setTitle(dto.getTitle());
        entity.setAuthor(dto.getAuthor());
        entity.setIsbn(dto.getIsbn());
        entity.setCategory(dto.getCategory());
        entity.setTotalCopies(dto.getTotalCopies());
        entity.setAvailableCopies(dto.getAvailableCopies());
    }
}
