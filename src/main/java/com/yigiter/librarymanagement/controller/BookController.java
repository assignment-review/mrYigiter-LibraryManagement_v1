package com.yigiter.librarymanagement.controller;

import com.yigiter.librarymanagement.dto.BookDTO;
import com.yigiter.librarymanagement.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping("/add")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO bookDTO1 =bookService.createBook(bookDTO);
        return new ResponseEntity<>(bookDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/{isbn}")
    public  ResponseEntity<BookDTO> getBook(@PathVariable String isbn){
        BookDTO bookDTO =bookService.getBook(isbn);
        return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<String> removeBook(@PathVariable String title){
        bookService.removeBook(title);
        String message="Book deleted successfully";
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/borrow")
    public ResponseEntity<BookDTO> borrowBook(@RequestParam("title") String title){
        BookDTO bookDTO=bookService.borrowBook(title);
        return ResponseEntity.ok(bookDTO);
    }

    @PatchMapping("/return/{title}")
    public ResponseEntity<BookDTO> returnBook(@ PathVariable String title){
        BookDTO bookDTO=bookService.returnBook(title);
        return ResponseEntity.ok(bookDTO);
    }






       /*  // ****** bu endpointlerde Hibernate kullnılarak JPARepository ile yapılan sorgular için kullnılan methodlar ******

    *   @PostMapping("/add")
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {

            BookDTO bookDTO1 =bookService.createBook(bookDTO);
        return new ResponseEntity<>(bookDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/{isbn}")
    public  ResponseEntity<BookDTO> getBook(@PathVariable String isbn){
        BookDTO bookDTO =bookService.getBook(isbn);
       return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping("/borrow/{title}")
    public ResponseEntity<String> removeBook(@PathVariable String title){
        String message=bookService.removeBook(title);
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/borrow/{title}")
    public ResponseEntity<BookDTO> borrowBook(@ PathVariable String title){
        BookDTO bookDTO=bookService.borrowBook(title);
        return ResponseEntity.ok(bookDTO);
    }

    @PatchMapping("/return/{title}")
    public ResponseEntity<BookDTO> returnBook(@ PathVariable String title){
        BookDTO bookDTO=bookService.returnBook(title);
        return ResponseEntity.ok(bookDTO);
    }
    * */



}
