package com.markov.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewWorkerReqParam {

    @DecimalMin(value = "1", message = "the id should be positive")
    String id;

    @NotEmpty(message = "name can't be empty")
    String name;

    @NotEmpty(message = "the x coordinate can't be empty")
    @DecimalMax(value = "271", message = "the x coordinate must be a number no greater than 271")
    String xCoord;

    @NotEmpty(message = "the x coordinate can't be empty")
    @DecimalMax(value = "57", message = "the н coordinate must be a number no greater than 57")
    String yCoord;

    @NotEmpty(message = "salary can't be empty")
    @DecimalMin(value = "1", message = "the salary should be number higher 0")
    @Pattern(regexp = "^[1-9][0-9]+", message = "the salary should be number higher 0")
    String salary;

    @NotEmpty(message = "startDate can't be empty")
    @Pattern(regexp = "((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])")
    String startDate;

    @NotEmpty(message = "Position can't be empty")
    String position;

    @NotEmpty(message = "Status can't be empty")
    String status;

    @NotEmpty(message = "Organization can't be empty")
    @Min(0)
    String organizationId;
}
