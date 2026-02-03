package com.kaldis.chris.librij.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Min(1)
    @Column(name = "total_pages")
    private Integer totalPages;

    @Min(0)
    @Column(name = "current_page")
    private Integer currentPage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus bookStatus;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}
