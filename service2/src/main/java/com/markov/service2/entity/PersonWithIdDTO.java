package com.markov.service2.entity;

import com.markov.service2.entity.enums.EyeColor;
import lombok.Data;

@Data
public class PersonWithIdDTO extends AbstractDTO{

    private int id;
    private EyeColor eyeColor;
    private Integer height;
    private LocationWithIdDTO location;
    private String passportID;
}
