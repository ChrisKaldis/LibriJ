package com.kaldis.chris.librij.dto.book;

import com.kaldis.chris.librij.domain.BookStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetBookResponseDTO extends RepresentationModel<GetBookResponseDTO> {

    @JsonProperty(index = 1)
    private UUID bookId;

    @JsonProperty(index = 2)
    private String title;

    @JsonProperty(index = 3)
    private String author;

    @JsonProperty(index = 4)
    private Integer totalPages;

    @JsonProperty(index = 5)
    private Integer currentPage;

    @JsonProperty(index = 6)
    private BookStatus bookStatus;

    @JsonProperty(index = 7)
    private LocalDate startDate;

    @JsonProperty(index = 8)
    private LocalDate finishDate;

    @JsonProperty(index = 9)
    private String notes;

    @JsonProperty(index = 10)
    private UUID locationId;

    @JsonProperty(index = 11)
    private Instant createdAt;

    @JsonProperty(index = 12)
    private Instant updatedAt;

}
