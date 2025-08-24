package org.books.catalogService.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.*;

import java.time.Instant;

public record Book(
        @Id
        Long id,

        @Version
        Long version,

        @NotBlank(message = "The Book ISBN must be defined")
        @Pattern(regexp = "^ISBN-[0-9]{0,10}$", message = "The ISBN format must be valid")
        String isbn,

        @NotBlank(message = "The Book title must be defined")
        String title,

        @NotBlank(message = "The Book author must be defined")
        String author,

        @NotNull(message = "The Book price must be defined")
        @Positive(message = "The Book price must be greater than zero.")
        Double price,

        @CreatedDate
        Instant createdAt,

        @LastModifiedDate
        Instant updatedAt,

        @CreatedBy
        String createdBy,

        @LastModifiedBy
        String updatedBy
) {
        public static Book of(String isbn, String title, String author, Double price) {
                return new Book(null, null, isbn, title, author, price, null, null, null, null);
        }
}
