package org.books.catalogService.exceptions;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbn) {
        super("A Book with ISBN " + isbn + " already exists.");
    }
}
