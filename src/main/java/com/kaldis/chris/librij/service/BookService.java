package com.kaldis.chris.librij.service;

import com.kaldis.chris.librij.dto.book.CreateBookRequestDTO;
import com.kaldis.chris.librij.dto.book.GetBookResponseDTO;
import com.kaldis.chris.librij.dto.book.UpdateBookRequestDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.UUID;

public interface BookService {

    GetBookResponseDTO createBook(CreateBookRequestDTO createBookRequestDTO);
    GetBookResponseDTO readBook(UUID bookId);
    GetBookResponseDTO updateBook(UUID bookId, UpdateBookRequestDTO updateBookRequestDTO);
    void deleteBook(UUID bookId);

    CollectionModel<GetBookResponseDTO> findAllBooks();
    CollectionModel<GetBookResponseDTO> findByLocationId(UUID locationId);

}
