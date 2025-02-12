package org.polar.catalogservice.services;

import org.polar.catalogservice.domain.Book;
import org.polar.catalogservice.domain.BookRepository;
import org.polar.catalogservice.exceptions.BookAlreadyExistsException;
import org.polar.catalogservice.exceptions.BookNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList(){
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn){
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book){
        if(bookRepository.existsByIsbn(book.isbn()))
            throw new BookAlreadyExistsException(book.isbn()) ;
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn){
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookFromCatalog(String isbn, Book book){
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> bookRepository.save(new Book(
                        existingBook.isbn(),
                        book.title(),
                        book.author(),
                        book.price()))
                ).orElseThrow(() -> new BookNotFoundException(isbn));
    }
}
