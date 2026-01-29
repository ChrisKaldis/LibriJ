package com.kaldis.chris.librij.mapper;

import com.kaldis.chris.librij.domain.Location;
import com.kaldis.chris.librij.dto.location.CreateLocationRequestDTO;
import com.kaldis.chris.librij.dto.location.GetLocationResponseDTO;
import com.kaldis.chris.librij.dto.location.UpdateLocationRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.hateoas.Link;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationMapper {

    @Mapping(target = "locationId", source = "id")
    GetLocationResponseDTO locationToGetLocationResponseDTO(Location location);

    Location createRequestDTOToLocation(CreateLocationRequestDTO createLocationRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateLocationFromUpdateRequestDTO(UpdateLocationRequestDTO updateLocationRequestDTO,
                                            @MappingTarget Location location);

    default GetLocationResponseDTO locationToGetLocationResponseDTOWithLinks(Location location) {
        GetLocationResponseDTO dto = locationToGetLocationResponseDTO(location);
        dto.add(Link.of("/api/v1/locations/" + dto.getLocationId()).withSelfRel());
        dto.add(Link.of("/api/v1/locations/" + dto.getLocationId() + "/books").withRel("books"));

        return dto;
    }

}
