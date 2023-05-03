package com.yigiter.librarymanagement.service;

import com.yigiter.librarymanagement.domain.Book;
import com.yigiter.librarymanagement.dto.BookDTO;
import com.yigiter.librarymanagement.repository.BookRepository;
import com.yigiter.librarymanagement.repository.BooksRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;// Bu JpaRepository için injetion yapıldı.
    private final BooksRepository booksRepository;
    public BookService(BookRepository bookRepository, BooksRepository booksRepository) {
        this.bookRepository = bookRepository;
        this.booksRepository = booksRepository;
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book bookTitle =booksRepository.getBookByTitle(bookDTO.getTitle());
        Book bookIsbn = booksRepository.getBookByIsbn(bookDTO.getIsbn());
            if (bookTitle!=null ){
                throw new RuntimeException("Book title already exist");
            } else if (bookIsbn!=null) {
                throw new RuntimeException("Book isbn already exist");
            } else {
                booksRepository.createBook(bookDTO);
            }
            return bookDTO;
    }

    public BookDTO getBook(String isbn) {
        Book book= booksRepository.getBookByIsbn(isbn);
            if (book==null){
                throw new RuntimeException("Book not found");
            }
            return bookToBookDTO(book);
    }

    public void removeBook(String title) {
        Book book= booksRepository.getBookByTitle(title);
            if (book!=null){
                booksRepository.removeBook(title);
            }else {
                throw new RuntimeException("Book not found");
            }
    }

    public BookDTO borrowBook(String title) {
      Book book=  booksRepository.getBookByTitle(title);
        if (book==null ){
            throw new RuntimeException("Book not found");
        } else if (!(book.getAvailableCopies()>0)){
            throw new RuntimeException("The book is not available in our library ");
        } else {
            int availableCopies=book.getAvailableCopies()-1;
            book.setAvailableCopies(availableCopies);
            booksRepository.borrowReturnBook(title,availableCopies);
        }
        return bookToBookDTO(book);
    }

    public BookDTO returnBook(String title) {
      Book book= booksRepository.getBookByTitle(title);
        if (book==null ){
            throw new RuntimeException("Book not found");
        }else {
           int availableCopies=book.getAvailableCopies()+1;
            book.setAvailableCopies(availableCopies);// burada max capacity tutan attribute olmadığı için return işleminde hep 1 ekler
           booksRepository.borrowReturnBook(title,availableCopies);
        }
        return bookToBookDTO(book);
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









