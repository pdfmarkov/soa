package com.markov.entities.mappers;

import com.markov.entities.Worker;
import com.markov.entities.dto.WorkerDTO;
import com.markov.entities.dto.WorkerWithIdDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface WorkerMapper {

    String dateFormat = "yyyy-MM-dd";
    String localDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";
    String zonedDateTimeFormat = "MM/dd/yyyy - HH:mm:ss Z";

    @Mapping(target = "startDate", dateFormat = zonedDateTimeFormat)
    @Mapping(target = "creationDate", dateFormat = dateFormat)
    @Mapping(target = "endDate",dateFormat = localDateTimeFormat)
    WorkerWithIdDTO convertWorkerToWorkerWithIdDTO(Worker worker);

    @Mapping(target = "startDate", dateFormat = zonedDateTimeFormat)
    @Mapping(target = "endDate",dateFormat = localDateTimeFormat)
    WorkerDTO convertWorkerToWorkerDTO(Worker worker);

    @Mapping(target = "startDate", dateFormat = zonedDateTimeFormat)
    @Mapping(target = "creationDate",
            expression = "java(new java.sql.Date(System.currentTimeMillis()))")
    @Mapping(target = "endDate", dateFormat = localDateTimeFormat)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coordinates.id", ignore = true)
    @Mapping(target = "person.id", ignore = true)
    @Mapping(target = "person.location.id", ignore = true)
    Worker convertWorkerDTOtoWorker(WorkerDTO workerDTO);
}
