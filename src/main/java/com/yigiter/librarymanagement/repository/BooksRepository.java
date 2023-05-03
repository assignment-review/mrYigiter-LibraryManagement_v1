package com.yigiter.librarymanagement.repository;

import com.yigiter.librarymanagement.domain.Book;
import com.yigiter.librarymanagement.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BooksRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createBook(BookDTO bookDTO){
        String sql2 = "INSERT INTO tbl_books (title, author, isbn, available_copies) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql2,bookDTO.getTitle(),bookDTO.getAuthor(),bookDTO.getIsbn(),bookDTO.getAvailableCopies());
    }

    public void removeBook(String title){
        jdbcTemplate.update("DELETE FROM tbl_books WHERE title='"+title+"'");
    }

    public void borrowReturnBook(String title, int availableCopies){
        jdbcTemplate.update("UPDATE tbl_books SET available_copies ="+availableCopies+ " WHERE title='"+title+"'");
    }

    public Book getBookByIsbn(String isbn) {
        Book book;
        String sql="SELECT * FROM tbl_books WHERE isbn='"+isbn+"'";
        try {
            book=jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Book.class));
            return book;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public Book getBookByTitle(String title) {
        Book book;
        String sql="SELECT * FROM tbl_books WHERE title='"+title+"'";
        try {
            book=jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Book.class));
            return book;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

}
