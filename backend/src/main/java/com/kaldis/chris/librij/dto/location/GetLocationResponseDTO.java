package com.kaldis.chris.librij.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetLocationResponseDTO extends RepresentationModel<GetLocationResponseDTO> {

    @JsonProperty(index = 1)
    private UUID locationId;

    @JsonProperty(index = 2)
    private String house;

    @JsonProperty(index = 3)
    private String bookcase;

    @JsonProperty(index = 4)
    private Instant createdAt;

    @JsonProperty(index = 5)
    private Instant updatedAt;

}
