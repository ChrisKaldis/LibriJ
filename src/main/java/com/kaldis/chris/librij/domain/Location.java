package com.kaldis.chris.librij.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location extends AbstractEntity {

    @Column(name = "house", nullable = false)
    private String house;

    @Column(name = "bookcase", nullable = false)
    private String bookcase;

}
