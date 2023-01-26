package com.markov.service2.entity;

import com.markov.service2.entity.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkerWithIdDTO extends AbstractDTO{

    private Integer id;
    private String creationDate;
    private String name;
    private String startDate;
    private Long salary;
    private String endDate;
    private Position position;
    private CoordinatesDTO coordinates;
    private PersonWithIdDTO person;
    private OrganizationWithIdDTO organization;
}
