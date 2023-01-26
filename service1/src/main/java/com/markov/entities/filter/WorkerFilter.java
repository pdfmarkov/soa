package com.markov.entities.filter;

import com.markov.entities.dto.CoordinatesDTO;
import com.markov.entities.dto.PersonDTO;
import com.markov.entities.enums.Position;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkerFilter {

    private Integer id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private String startDate; //Поле не может быть null
    private String endDate; //Поле не может быть null
    private Position position; //Поле может быть null

    private CoordinatesDTO coordinates; //Поле не может быть null
    private PersonDTO person; //Поле может быть null
}
