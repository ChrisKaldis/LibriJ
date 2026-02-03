package com.kaldis.chris.librij.service;

import com.kaldis.chris.librij.dto.location.CreateLocationRequestDTO;
import com.kaldis.chris.librij.dto.location.GetLocationResponseDTO;
import com.kaldis.chris.librij.dto.location.UpdateLocationRequestDTO;
import org.springframework.hateoas.CollectionModel;

import java.util.UUID;

public interface LocationService {

    GetLocationResponseDTO createLocation(CreateLocationRequestDTO createLocationRequestDTO);
    GetLocationResponseDTO readLocation(UUID locationId);
    GetLocationResponseDTO updateLocation(UUID locationId, UpdateLocationRequestDTO updateLocationRequestDTO);
    void deleteLocation(UUID locationId);

    CollectionModel<GetLocationResponseDTO> findAllLocations(int page, int size);

}
