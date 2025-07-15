package com.example.lms.service;

import com.example.lms.dto.BookDTO;
import org.springframework.data.domain.*;

public interface BookService {
    BookDTO addBook(BookDTO dto);
    BookDTO updateBook(Long id, BookDTO dto);
    void    deleteBook(Long id);
    BookDTO getBook(Long id);
    Page<BookDTO> listBooks(Pageable pageable);
    Page<BookDTO> searchBooks(String query, Pageable pageable);
}
