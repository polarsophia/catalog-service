package org.polar.catalogservice.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface BookRepository {
    Iterable<Book> findAll();
    boolean existsByIsbn(String isbn);
    Optional<Book> findByIsbn(String isbn);
    Book save(Book book);
    void deleteByIsbn(String isbn);
}
