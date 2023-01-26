package com.markov.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.markov.entities.dto.AbstractDTO;
import com.markov.entities.dto.LocationDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "location")
public class Location extends AbstractEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//, generator="vehicle-gen")
    private int id;

    @Column(name = "x_coord")
    private Float x;
    @Column(name = "y_coord")
    private Long y;
    @Column(name = "z_coord")
    private Double z;
    @Column(name = "name")
    private String name;
}
