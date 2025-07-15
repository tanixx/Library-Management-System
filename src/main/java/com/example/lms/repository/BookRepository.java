package com.example.lms.repository;

import com.example.lms.entity.Book;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
           SELECT b FROM Book b
           WHERE lower(b.title)   LIKE lower(concat('%', :q, '%'))
              OR lower(b.author)  LIKE lower(concat('%', :q, '%'))
              OR lower(b.isbn)    LIKE lower(concat('%', :q, '%'))
           """)
    Page<Book> search(@Param("q") String query, Pageable pageable);
}
