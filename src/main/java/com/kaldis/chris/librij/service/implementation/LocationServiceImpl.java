package com.kaldis.chris.librij.service.implementation;

import com.kaldis.chris.librij.data.jpa.repository.LocationRepository;
import com.kaldis.chris.librij.domain.Location;
import com.kaldis.chris.librij.dto.location.CreateLocationRequestDTO;
import com.kaldis.chris.librij.dto.location.GetLocationResponseDTO;
import com.kaldis.chris.librij.dto.location.UpdateLocationRequestDTO;
import com.kaldis.chris.librij.exception.ResourceNotFound;
import com.kaldis.chris.librij.mapper.LocationMapper;
import com.kaldis.chris.librij.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public CollectionModel<GetLocationResponseDTO> findAllLocations() {
        List<GetLocationResponseDTO> locations = locationRepository.findAll()
                .stream()
                .map(locationMapper::locationToGetLocationResponseDTOWithLinks)
                .toList();

        return CollectionModel.of(locations, Link.of("/api/v1/locations").withSelfRel());
    }

    @Override
    public GetLocationResponseDTO createLocation(CreateLocationRequestDTO createLocationRequestDTO) {
        Location location = locationMapper.createRequestDTOToLocation(createLocationRequestDTO);
        location = locationRepository.save(location);

        return locationMapper.locationToGetLocationResponseDTOWithLinks(location);
    }

    @Override
    public GetLocationResponseDTO readLocation(UUID locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFound("Location for the given location id not found."));

        return locationMapper.locationToGetLocationResponseDTOWithLinks(location);
    }

    @Override
    public GetLocationResponseDTO updateLocation(UUID locationId, UpdateLocationRequestDTO updateLocationRequestDTO) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFound("Location for the given location id not found."));
        
        locationMapper.updateLocationFromUpdateRequestDTO(updateLocationRequestDTO, location);
        location = locationRepository.save(location);

        return locationMapper.locationToGetLocationResponseDTOWithLinks(location);
    }

    @Override
    public void deleteLocation(UUID locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFound("Location for the given location id not found."));
        locationRepository.delete(location);
    }

}
