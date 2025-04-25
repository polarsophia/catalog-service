package org.books.catalogService;

import org.junit.jupiter.api.Test;
import org.books.catalogService.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PolarsophiaApplicationTests {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void whenPostRequestThenBookCreated(){
		var expectedBook = Book.of("ISBN-09", "Cloud Native In Action", "Thomas Vitale", 699.0);
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

