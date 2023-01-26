package com.markov.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UPDWorkerParam {

    @DecimalMin(value = "1", message = "the id should be positive")
    String id;

    String name;

    @DecimalMax(value = "271", message = "the x coordinate must be a number no greater than 271")
    String xCoord;

    @DecimalMax(value = "57", message = "the н coordinate must be a number no greater than 57")
    String yCoord;

    @DecimalMin(value = "1", message = "the salary should be number higher 0")
    @Pattern(regexp = "^[1-9][0-9]+", message = "the salary should be number higher 0")
    String salary;

    @Pattern(regexp = "((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")
    String startDate;

    String position;

    String status;

    String creationDate;

    @Min(0)
    String organizationId;
}
