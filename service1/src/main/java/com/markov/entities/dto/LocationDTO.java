package com.markov.entities.dto;

import lombok.Data;

@Data
public class LocationDTO extends AbstractDTO {

    private Float x;
    private Long y;
    private Double z;
    private String name;
}
