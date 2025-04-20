package org.books.catalogService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.books.catalogService.controllers.BookController;
import org.books.catalogService.domain.Book;
import org.books.catalogService.exceptions.BookNotFoundException;
import org.books.catalogService.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PolarsophiaApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void whenPostRequestThenBookCreated(){
		var expectedBook = new Book("ISBN-09", "Cloud Native In Action", "Thomas Vitale", 699.0);
		webTestClient
				.post()
				.uri("/books")
				.bodyValue(expectedBook)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(actualBook -> {
					assertThat(actualBook).isNotNull();
					assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
				});
	}
}

@WebMvcTest(BookController.class)
class BookControllerIntegrationTest{
	@Autowired
	private MockMvc mockMvc;
	@MockitoBean
	private BookService bookService;

	@Test
	void whenGetBookNotExistsThenReturn404() throws Exception {
		String isbn = "ISBN-09";
		Mockito.when(bookService.viewBookDetails(isbn)).thenThrow(BookNotFoundException.class);
		mockMvc
				.perform(MockMvcRequestBuilders.get("/books/" + isbn))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}

@JsonTest
class JsonBookTest{
	@Autowired
	public JacksonTester<Book> json;

	@Test
	void testSerialize() throws IOException {
		var book = new Book("ISBN-09", "Cloud Native In Action", "Thomas Vitale", 699.0);
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
					"price" : 699.0
				}
				""";
		assertThat(json.parse(jsonContent))
				.isEqualTo(new Book("ISBN-09", "Cloud Native In Action", "Thomas Vitale", 699.0));
	}
}

