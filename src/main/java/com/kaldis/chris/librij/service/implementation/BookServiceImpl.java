package com.kaldis.chris.librij.service.implementation;

import com.kaldis.chris.librij.assembler.BookAssembler;
import com.kaldis.chris.librij.data.jpa.repository.BookRepository;
import com.kaldis.chris.librij.data.jpa.repository.LocationRepository;
import com.kaldis.chris.librij.domain.Book;
import com.kaldis.chris.librij.domain.Location;
import com.kaldis.chris.librij.dto.book.CreateBookRequestDTO;
import com.kaldis.chris.librij.dto.book.GetBookResponseDTO;
import com.kaldis.chris.librij.dto.book.UpdateBookRequestDTO;
import com.kaldis.chris.librij.exception.ResourceNotFound;
import com.kaldis.chris.librij.mapper.BookMapper;
import com.kaldis.chris.librij.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final LocationRepository locationRepository;

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private final BookAssembler bookAssembler;
    private final PagedResourcesAssembler<Book> pagedResourcesAssembler;

    @Override
    public CollectionModel<GetBookResponseDTO> findAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepository.findAll(pageable);

        var bookResponseDTOPagedModel = pagedResourcesAssembler.toModel(books, bookAssembler);

        return bookResponseDTOPagedModel
                .add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString()).withSelfRel());
    }

    @Override
    public CollectionModel<GetBookResponseDTO> findByLocationId(UUID locationId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepository.findAllByLocationId(locationId, pageable);

        var bookResponseDTOPagedModel = pagedResourcesAssembler.toModel(books, bookAssembler);

        return bookResponseDTOPagedModel
                .add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString()).withSelfRel());
    }

    @Override
    public GetBookResponseDTO createBook(CreateBookRequestDTO createBookRequestDTO) {
        Book book = bookMapper.createRequestDTOtoBook(createBookRequestDTO);
        if (createBookRequestDTO.getLocationId() != null) {
            Location location = locationRepository.findById(createBookRequestDTO.getLocationId())
                    .orElseThrow(() -> new ResourceNotFound("Location for the given new book not found."));
            book.setLocation(location);
        }
        book = bookRepository.save(book);

        return bookAssembler.toModel(book);
    }

    @Override
    public GetBookResponseDTO readBook(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book for the given book id not found."));

        return bookAssembler.toModel(book);
    }

    @Override
    public GetBookResponseDTO updateBook(UUID bookId, UpdateBookRequestDTO updateBookRequestDTO) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book for the given book id not found."));
        
        bookMapper.updateBookFromUpdateRequestDTO(updateBookRequestDTO, book);
        if (updateBookRequestDTO.getLocationId() != null
                && !updateBookRequestDTO.getLocationId().equals(book.getLocation().getId())) {
            var location = locationRepository.findById(updateBookRequestDTO.getLocationId())
                    .orElseThrow(() -> new ResourceNotFound("Location for the given book id not found."));
            book.setLocation(location);
        }
        book = bookRepository.save(book);

        return bookAssembler.toModel(book);
    }

    @Override
    public void deleteBook(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book for the given book id not found."));
        bookRepository.delete(book);
    }

}
