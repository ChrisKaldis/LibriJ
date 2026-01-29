package com.kaldis.chris.librij.api;

import com.kaldis.chris.librij.dto.book.GetBookResponseDTO;
import com.kaldis.chris.librij.dto.location.CreateLocationRequestDTO;
import com.kaldis.chris.librij.dto.location.GetLocationResponseDTO;
import com.kaldis.chris.librij.dto.location.UpdateLocationRequestDTO;
import com.kaldis.chris.librij.service.BookService;
import com.kaldis.chris.librij.service.LocationService;
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
@RequestMapping("api/v1/locations")
public class LocationController {

    private final LocationService locationService;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<CollectionModel<GetLocationResponseDTO>> findAllLocations() {
        var locationResponseDTOCollectionModel = locationService.findAllLocations();

        return ResponseEntity.ok(locationResponseDTOCollectionModel);
    }

    @PostMapping
    public ResponseEntity<GetLocationResponseDTO> saveLocation(@Validated @RequestBody
                                                               CreateLocationRequestDTO createLocationRequestDTO) {
        var locationResponseDTO = locationService.createLocation(createLocationRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(locationResponseDTO);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<GetLocationResponseDTO> findLocationById(@PathVariable UUID locationId) {
        var locationResponseDTO = locationService.readLocation(locationId);

        return ResponseEntity.ok(locationResponseDTO);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<GetLocationResponseDTO> updateLocation(@PathVariable UUID locationId,
                                                                 @Validated @RequestBody
                                                                 UpdateLocationRequestDTO updateLocationRequestDTO) {
        var locationResponseDTO = locationService.updateLocation(locationId, updateLocationRequestDTO);

        return ResponseEntity.ok(locationResponseDTO);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID locationId) {
        locationService.deleteLocation(locationId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{locationId}/books")
    public ResponseEntity<CollectionModel<GetBookResponseDTO>> findLocationAllBooks(@PathVariable UUID locationId) {
        var bookResponseDTOCollectionModel = bookService.findByLocationId(locationId);

        return ResponseEntity.ok(bookResponseDTOCollectionModel);
    }

}
