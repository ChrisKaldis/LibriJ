package com.kaldis.chris.librij.service.implementation;

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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final LocationRepository locationRepository;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public CollectionModel<GetBookResponseDTO> findAllBooks() {
        List<GetBookResponseDTO> books = bookRepository.findAll()
                .stream()
                .map(bookMapper::bookToGetBookResponseDTOWithLinks)
                .toList();

        return CollectionModel.of(books, Link.of("api/v1/books").withSelfRel());
    }

    @Override
    public CollectionModel<GetBookResponseDTO> findByLocationId(UUID locationId) {
        List<GetBookResponseDTO> books = bookRepository.findAllByLocationId(locationId)
                .stream()
                .map(bookMapper::bookToGetBookResponseDTOWithLinks)
                .toList();

        return CollectionModel.of(books, Link.of("api/v1/locaion/" + locationId + "/books").withSelfRel());
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

        return bookMapper.bookToGetBookResponseDTOWithLinks(book);
    }

    @Override
    public GetBookResponseDTO readBook(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book for the given book id not found."));
        GetBookResponseDTO bookResponseDTO = bookMapper.bookToGetBookResponseDTOWithLinks(book);
        bookResponseDTO.setLocationId(book.getLocation().getId());

        return bookResponseDTO;
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

        GetBookResponseDTO bookResponseDTO = bookMapper.bookToGetBookResponseDTOWithLinks(book);
        if (book.getLocation() != null) {
            bookResponseDTO.setLocationId(book.getLocation().getId());
        }

        return bookResponseDTO;
    }

    @Override
    public void deleteBook(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book for the given book id not found."));
        bookRepository.delete(book);
    }

}
