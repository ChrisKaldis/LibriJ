package com.kaldis.chris.librij.assembler;

import com.kaldis.chris.librij.api.LocationController;
import com.kaldis.chris.librij.domain.Location;
import com.kaldis.chris.librij.dto.location.GetLocationResponseDTO;
import com.kaldis.chris.librij.mapper.LocationMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class LocationAssembler extends RepresentationModelAssemblerSupport<Location, GetLocationResponseDTO> {

    private final LocationMapper locationMapper;

    public LocationAssembler(LocationMapper locationMapper) {
        super(LocationController.class, GetLocationResponseDTO.class);
        this.locationMapper = locationMapper;
    }

    @Override
    public GetLocationResponseDTO toModel(Location location) {
        GetLocationResponseDTO dto = locationMapper.locationToGetLocationResponseDTO(location);
        Link selfLink = linkTo(LocationController.class).slash(location.getId()).withSelfRel();
        Link booksLink = linkTo(LocationController.class).slash(location.getId() + "/books").withRel("books");

        dto.add(selfLink).add(booksLink);

        return dto;
    }

}
