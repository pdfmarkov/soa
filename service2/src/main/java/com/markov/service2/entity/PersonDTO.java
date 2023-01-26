package com.markov.service2.entity;


import com.markov.service2.entity.enums.EyeColor;
import lombok.Data;

@Data
public class PersonDTO extends AbstractDTO {

    private EyeColor eyeColor;
    private Integer height;
    private LocationDTO location;
    private String passportID;
}
