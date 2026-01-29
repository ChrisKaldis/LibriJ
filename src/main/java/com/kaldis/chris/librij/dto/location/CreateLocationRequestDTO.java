package com.kaldis.chris.librij.dto.location;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLocationRequestDTO {

    @NotNull
    String house;
    @NotNull
    String bookcase;

}
