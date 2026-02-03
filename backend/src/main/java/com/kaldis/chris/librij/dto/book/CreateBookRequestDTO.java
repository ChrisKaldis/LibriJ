package com.kaldis.chris.librij.dto.book;

import com.kaldis.chris.librij.domain.BookStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateBookRequestDTO {

    @NotNull
    String title;

    @NotNull
    String author;

    @Min(value = 1)
    Integer totalPages;

    @Min(value = 0)
    Integer currentPage;

    BookStatus bookStatus;

    LocalDate startDate;

    LocalDate finishDate;

    String notes;

    UUID locationId;

}
