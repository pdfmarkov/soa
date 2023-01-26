package com.markov.entities.dto;

import com.markov.entities.enums.EyeColor;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PersonDTO extends AbstractDTO {

    private EyeColor eyeColor;
    @Min(1)
    private Integer height;
    private LocationDTO location;
    private String passportID;
}
