package com.example.lms.controller;

import com.example.lms.dto.BookDTO;
import com.example.lms.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService; // Service to handle book operations

    // Add a new book (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO dto) {
        BookDTO saved = bookService.addBook(dto); // Save book using service
        return ResponseEntity.ok(saved); // Return the saved book
    }

    // Update an existing book by ID (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id,
                                              @RequestBody BookDTO dto) {
        BookDTO updated = bookService.updateBook(id, dto); // Update book
        return ResponseEntity.ok(updated); // Return updated book
    }

    // Delete a book by ID (Admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id); // Delete the book
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    // Get details of a specific book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id)); // Fetch and return book
    }

    // List all books with pagination and sorting
    @GetMapping
    public ResponseEntity<Page<BookDTO>> listBooks(
            @RequestParam(defaultValue = "0") int page, // Page number
            @RequestParam(defaultValue = "10") int size, // Page size
            @RequestParam(defaultValue = "title") String sortBy, // Sort field
            @RequestParam(defaultValue = "asc") String dir) { // Sort direction

        // Determine sort direction
        Sort sort = dir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort); // Create pageable
        return ResponseEntity.ok(bookService.listBooks(pageable)); // Return paginated books
    }

    // Search books by keyword with pagination
    @GetMapping("/search")
    public ResponseEntity<Page<BookDTO>> searchBooks(
            @RequestParam String query, // Search keyword
            @RequestParam(defaultValue = "0") int page, // Page number
            @RequestParam(defaultValue = "10") int size) { // Page size

        Pageable pageable = PageRequest.of(page, size); // Create pageable
        return ResponseEntity.ok(bookService.searchBooks(query, pageable)); // Return search results
    }
}
