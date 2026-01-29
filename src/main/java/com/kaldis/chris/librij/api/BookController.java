package com.kaldis.chris.librij.api;

import com.kaldis.chris.librij.dto.book.CreateBookRequestDTO;
import com.kaldis.chris.librij.dto.book.GetBookResponseDTO;
import com.kaldis.chris.librij.dto.book.UpdateBookRequestDTO;
import com.kaldis.chris.librij.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<CollectionModel<GetBookResponseDTO>> findAll() {
        var bookResponseDTOCollectionModel = bookService.findAllBooks();

        return ResponseEntity.ok(bookResponseDTOCollectionModel);
    }

    @PostMapping
    public ResponseEntity<GetBookResponseDTO> saveBook(@Validated @RequestBody
                                                           CreateBookRequestDTO createBookRequestDTO) {
        var bookResponseDTO = bookService.createBook(createBookRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseDTO);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<GetBookResponseDTO> findBookById(@PathVariable UUID bookId) {
        var bookResponseDTO = bookService.readBook(bookId);

        return ResponseEntity.ok(bookResponseDTO);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<GetBookResponseDTO> updateBook(@PathVariable UUID bookId,
                                                         @Validated @RequestBody
                                                         UpdateBookRequestDTO updateBookRequestDTO) {
        var bookResponseDTO = bookService.updateBook(bookId, updateBookRequestDTO);

        return ResponseEntity.ok(bookResponseDTO);
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID bookId) {
        bookService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }

}
