package com.markov.entities.dto;

import com.markov.entities.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkerWithIdDTO extends AbstractDTO{

    private Integer id;
    private String creationDate;
    @NotBlank(message = "Worker must have name!")
    private String name;
    @NotBlank
    private String startDate;
    private Long salary;
    private String endDate;
    private Position position;
    @NotNull
    private CoordinatesDTO coordinates;
    private PersonWithIdDTO person;
    private OrganizationWithIdDTO organization;
}
