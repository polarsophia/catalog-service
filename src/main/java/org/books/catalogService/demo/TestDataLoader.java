package org.books.catalogService.demo;

import org.books.catalogService.domain.Book;
import org.books.catalogService.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testData")
public class TestDataLoader {
    private final BookRepository bookRepository;

    public TestDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData() {
        bookRepository.save(new Book("ISBN-978", "The Great Gatsby", "F. Scott Fitzgerald", 10.99));
        bookRepository.save(new Book("ISBN-909", "To Kill a Mockingbird", "Harper Lee", 12.99));
        bookRepository.save(new Book("ISBN-356", "1984", "George Orwell", 15.99));
        bookRepository.save(new Book("ISBN-284", "Pride and Prejudice", "Jane Austen", 8.99));
    }
}
