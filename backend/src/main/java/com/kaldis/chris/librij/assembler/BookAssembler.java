package com.kaldis.chris.librij.assembler;

import com.kaldis.chris.librij.api.BookController;
import com.kaldis.chris.librij.api.LocationController;
import com.kaldis.chris.librij.domain.Book;
import com.kaldis.chris.librij.dto.book.GetBookResponseDTO;
import com.kaldis.chris.librij.mapper.BookMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class BookAssembler extends RepresentationModelAssemblerSupport<Book, GetBookResponseDTO> {

    private final BookMapper bookMapper;

    public BookAssembler(BookMapper bookMapper) {
        super(BookController.class, GetBookResponseDTO.class);
        this.bookMapper = bookMapper;
    }

    @Override
    public GetBookResponseDTO toModel(Book book) {
        GetBookResponseDTO dto = bookMapper.bookToGetBookResponseDTO(book);
        Link selfLink = linkTo(BookController.class).slash(book.getId()).withSelfRel();

        dto.add(selfLink);

        if (book.getLocation() != null) {
            dto.setLocationId(book.getLocation().getId());
        }

        if (dto.getLocationId() != null) {
            Link locationLink = linkTo(LocationController.class).slash(dto.getLocationId()).withSelfRel();
            dto.add(locationLink);
        }

        return dto;
    }

}
