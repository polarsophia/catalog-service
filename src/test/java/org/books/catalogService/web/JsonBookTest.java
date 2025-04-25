package org.books.catalogService.web;

import org.books.catalogService.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class JsonBookTest {
    @Autowired
    public JacksonTester<Book> json;

    @Test
    void testSerialize() throws IOException {
        var book = Book.of("ISBN-09", "Cloud Native In Action", "Thomas Vitale", 699.0);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());

    }

    @Test
    void testDeserialize() throws IOException {
        var jsonContent = """
                {
                	"isbn" : "ISBN-09",
                	"title" : "Cloud Native In Action",
                	"author" : "Thomas Vitale",
                	"price" : 699.0,
                	"version" : 0
                }
                """;
        assertThat(json.parse(jsonContent))
                .isEqualTo(Book.of("ISBN-09", "Cloud Native In Action", "Thomas Vitale", 699.0));
    }
}
