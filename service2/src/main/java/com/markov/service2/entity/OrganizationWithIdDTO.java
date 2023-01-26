package com.markov.service2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationWithIdDTO extends OrganizationDTO{
    private Integer id;
}
