package com.kaldis.chris.librij.service.implementation;

import com.kaldis.chris.librij.assembler.LocationAssembler;
import com.kaldis.chris.librij.data.jpa.repository.LocationRepository;
import com.kaldis.chris.librij.domain.Location;
import com.kaldis.chris.librij.dto.location.CreateLocationRequestDTO;
import com.kaldis.chris.librij.dto.location.GetLocationResponseDTO;
import com.kaldis.chris.librij.dto.location.UpdateLocationRequestDTO;
import com.kaldis.chris.librij.exception.ResourceNotFound;
import com.kaldis.chris.librij.mapper.LocationMapper;
import com.kaldis.chris.librij.service.LocationService;
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
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    private final LocationAssembler locationAssembler;
    private final PagedResourcesAssembler<Location> pagedResourcesAssembler;

    @Override
    public CollectionModel<GetLocationResponseDTO> findAllLocations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Location> locations = locationRepository.findAll(pageable);

        var locationResponseDTOPagedModel = pagedResourcesAssembler.toModel(locations, locationAssembler);

        return locationResponseDTOPagedModel
                .add(Link.of(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString()).withSelfRel());
    }

    @Override
    public GetLocationResponseDTO createLocation(CreateLocationRequestDTO createLocationRequestDTO) {
        Location location = locationMapper.createRequestDTOToLocation(createLocationRequestDTO);
        location = locationRepository.save(location);

        return locationAssembler.toModel(location);
    }

    @Override
    public GetLocationResponseDTO readLocation(UUID locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFound("Location for the given location id not found."));

        return locationAssembler.toModel(location);
    }

    @Override
    public GetLocationResponseDTO updateLocation(UUID locationId, UpdateLocationRequestDTO updateLocationRequestDTO) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFound("Location for the given location id not found."));
        
        locationMapper.updateLocationFromUpdateRequestDTO(updateLocationRequestDTO, location);
        location = locationRepository.save(location);

        return locationAssembler.toModel(location);
    }

    @Override
    public void deleteLocation(UUID locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFound("Location for the given location id not found."));
        locationRepository.delete(location);
    }

}
