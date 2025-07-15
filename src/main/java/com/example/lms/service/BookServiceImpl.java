package com.example.lms.service;

import com.example.lms.dto.BookDTO;
import com.example.lms.entity.Book;
import com.example.lms.exception.BookNotFoundException;
import com.example.lms.mapper.BookMapper;
import com.example.lms.repository.BookRepository;
import com.example.lms.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repo;

    @Override
    public BookDTO addBook(BookDTO dto) {
        Book book = BookMapper.toEntity(dto);
        book.setAvailableCopies(dto.getTotalCopies());
        return BookMapper.toDTO(repo.save(book));
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO dto) {
        Book existing = repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        BookMapper.copy(dto, existing);
        return BookMapper.toDTO(repo.save(existing));
    }

    @Override
    public void deleteBook(Long id) {
        if (!repo.existsById(id)) throw new BookNotFoundException(id);
        repo.deleteById(id);
    }

    @Override
    public BookDTO getBook(Long id) {
        return repo.findById(id)
                .map(BookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Page<BookDTO> listBooks(Pageable pageable) {
        return repo.findAll(pageable).map(BookMapper::toDTO);
    }

    @Override
    public Page<BookDTO> searchBooks(String query, Pageable pageable) {
        return repo.search(query, pageable).map(BookMapper::toDTO);
    }
}
