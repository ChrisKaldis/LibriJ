package com.kaldis.chris.librij.mapper;

import com.kaldis.chris.librij.domain.Book;
import com.kaldis.chris.librij.dto.book.CreateBookRequestDTO;
import com.kaldis.chris.librij.dto.book.GetBookResponseDTO;
import com.kaldis.chris.librij.dto.book.UpdateBookRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.hateoas.Link;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    @Mapping(target = "bookId", source = "id")
    GetBookResponseDTO bookToGetBookResponseDTO(Book book);

    Book createRequestDTOtoBook(CreateBookRequestDTO createBookRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    void updateBookFromUpdateRequestDTO(UpdateBookRequestDTO updateBookRequestDTO, @MappingTarget Book book);

    default GetBookResponseDTO bookToGetBookResponseDTOWithLinks(Book book) {
        GetBookResponseDTO dto = bookToGetBookResponseDTO(book);
        dto.add(Link.of("/api/v1/books/" + dto.getBookId()).withSelfRel());
        if (dto.getLocationId() != null) {
            dto.add(Link.of("/api/v1/locations/" + dto.getLocationId()).withRel("location"));
        }

        return dto;
    }

}
