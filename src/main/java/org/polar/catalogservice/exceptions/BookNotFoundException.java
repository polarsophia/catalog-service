package org.polar.catalogservice.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super("The Book with ISBN " + isbn + " not found.");
    }
}
