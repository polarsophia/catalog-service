package org.books.catalogService.services;

import org.books.catalogService.domain.Book;
import org.books.catalogService.domain.BookRepository;
import org.books.catalogService.exceptions.BookAlreadyExistsException;
import org.books.catalogService.exceptions.BookNotFoundException;
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
                        existingBook.id(),
                        existingBook.version(),
                        existingBook.isbn(),
                        book.title(),
                        book.author(),
                        book.price(),
                        existingBook.createdAt(),
                        book.updatedAt(),
                        existingBook.createdBy(),
                        book.updatedBy()
                    ))
                ).orElseThrow(() -> new BookNotFoundException(isbn));
    }
}
