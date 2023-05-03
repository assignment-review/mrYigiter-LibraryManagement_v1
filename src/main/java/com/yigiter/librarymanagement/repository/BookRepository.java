package com.yigiter.librarymanagement.repository;

import com.yigiter.librarymanagement.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    Optional<Book> findByTitle(String title);

    boolean existsByTitle(String title);

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

}
