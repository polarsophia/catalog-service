package org.books.catalogService.web;

import org.books.catalogService.controllers.BookController;
import org.books.catalogService.exceptions.BookNotFoundException;
import org.books.catalogService.services.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(BookController.class)
class BookControllerIntegrationTest {
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
