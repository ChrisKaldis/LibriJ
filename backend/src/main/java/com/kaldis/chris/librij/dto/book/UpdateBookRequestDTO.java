package com.kaldis.chris.librij.dto.book;

import com.kaldis.chris.librij.domain.BookStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UpdateBookRequestDTO {

    @Min(value = 0)
    Integer currentPage;

    @Enumerated(EnumType.STRING)
    BookStatus bookStatus;

    LocalDate startDate;

    LocalDate finishDate;

    String notes;

    UUID locationId;

}
