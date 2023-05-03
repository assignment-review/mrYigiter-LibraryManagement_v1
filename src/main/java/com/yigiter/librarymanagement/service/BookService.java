package com.yigiter.librarymanagement.service;

import com.yigiter.librarymanagement.domain.Book;
import com.yigiter.librarymanagement.dto.BookDTO;
import com.yigiter.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final BookRepository bookRepository;
    public BookService( BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book bookTitle =getBookByTitle(bookDTO.getTitle());
        Book bookIsbn = getBookByIsbn(bookDTO.getIsbn());
            if (bookTitle!=null ){
                throw new RuntimeException("Book title already exist");
            } else if (bookIsbn!=null) {
                throw new RuntimeException("Book isbn already exist");
            } else {
                String sql2 = "INSERT INTO tbl_books (title, author, isbn, available_copies) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(sql2,bookDTO.getTitle(),bookDTO.getAuthor(),bookDTO.getIsbn(),bookDTO.getAvailableCopies());
            }
            return bookDTO;
    }

    public BookDTO getBook(String isbn) {
        Book book= getBookByIsbn(isbn);
            if (book==null){
                throw new RuntimeException("Book not found");
            }
            return bookToBookDTO(book);
    }

    public String removeBook(String title) {
        Book book= getBookByTitle(title);
            if (book!=null){
                jdbcTemplate.update("DELETE FROM tbl_books WHERE title='"+title+"'");
                return "Book deleted successfully";
            }else {
                throw new RuntimeException("Book not found");
            }
    }

    public BookDTO borrowBook(String title) {
      Book book=  getBookByTitle(title);
        if (book==null ){
            throw new RuntimeException("Book not found");
        } else if (!(book.getAvailableCopies()>0)){
            throw new RuntimeException("The book is not available in our library ");
        } else {
            book.setAvailableCopies(book.getAvailableCopies()-1);
            jdbcTemplate.update("UPDATE tbl_books SET available_copies ="+book.getAvailableCopies() + " WHERE title='"+title+"'");
        }
        return bookToBookDTO(book);
    }

    public BookDTO returnBook(String title) {
      Book book=  getBookByTitle(title);
        if (book==null ){
            throw new RuntimeException("Book not found");
        }else {
            book.setAvailableCopies(book.getAvailableCopies()+1);// burada max capacity tutan attribute olmadığı için return işleminde hep 1 ekler
            jdbcTemplate.update("UPDATE tbl_books SET available_copies ="+book.getAvailableCopies() + " WHERE title='"+title+"'");
        }
        return bookToBookDTO(book);
    }

    //assist Methods
    private Book getBookByIsbn(String isbn) {
        Book book;
        String sql="SELECT * FROM tbl_books WHERE isbn='"+isbn+"'";
        try {
            book=jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Book.class));
            return book;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    private Book getBookByTitle(String title) {
        Book book;
        String sql="SELECT * FROM tbl_books WHERE title='"+title+"'";
        try {
            book=jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Book.class));
            return book;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    //convert methods
    private Book bookDTOToBook(BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setAvailableCopies(bookDTO.getAvailableCopies());
        return book;
    }
    private  BookDTO bookToBookDTO(Book book){
            BookDTO bookDTO =new BookDTO();
            bookDTO.setTitle(book.getTitle());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setIsbn(book.getIsbn());
            bookDTO.setAvailableCopies(book.getAvailableCopies());
            return bookDTO;
    }




    /* // *** burası jpaRepository uzeinden data ceken ve contorler katmanına gonderen methodlar

    *  public BookDTO createBook(BookDTO bookDTO) {
       boolean title= bookRepository.existsByTitle(bookDTO.getTitle());
       boolean isbn =bookRepository.existsByIsbn(bookDTO.getIsbn());
        if (title || isbn) {
            throw new RuntimeException("Book already exist");
        }
            Book book = bookDTOToBook(bookDTO);
        bookRepository.save(book);
        return bookDTO;
    }
    public BookDTO getBook(String isbn) {
        Book book= callBookIsbn(isbn);
         BookDTO bookDTO =bookToBookDTO(book);
         return bookDTO;
    }

    public String removeBook(String title) {
        Book book= callBook(title);
        bookRepository.delete(book);
        return "book deleted successfully";
    }

    public BookDTO borrowBook(String title) {
      Book book=  callBook(title);
      if (!(book.getAvailableCopies()>0)){
          throw new RuntimeException("The book is not available in our library ");
      }

      book.setAvailableCopies(book.getAvailableCopies()-1);
      bookRepository.save(book);
        return bookToBookDTO(book);
    }

    public BookDTO returnBook(String title) {
        Book book=  callBook(title);

        book.setAvailableCopies(book.getAvailableCopies()+1);
        bookRepository.save(book);
        return bookToBookDTO(book);
    }

    //DB den kitap getir
    private Book callBook( String title){
        Book book= bookRepository.findByTitle(title).orElseThrow(
                ()->new RuntimeException("Not found Book")
        );
        return book;
    }
    private Book callBookIsbn( String isbn){
        Book book= bookRepository.findByIsbn(isbn).orElseThrow(
                ()->new RuntimeException("Not found Book")
        );
        return book;
    }

    *
    * */



}









