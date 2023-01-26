package com.markov.resources;

import com.markov.entities.Worker;
import com.markov.entities.dto.ValueDto;
import com.markov.entities.dto.WorkerDTO;
import com.markov.entities.dto.WorkerWithIdDTO;
import com.markov.entities.enums.Position;
import com.markov.exceptions.BadRequestException;
import com.markov.exceptions.NoSuchWorkerException;

import javax.ejb.Local;
import javax.validation.constraints.DecimalMin;
import javax.ws.rs.FormParam;
import java.util.List;
import java.util.Map;

@Local
public interface WorkerService {

    WorkerWithIdDTO addNewWorker(WorkerDTO workerDTO) throws NoSuchWorkerException;

    WorkerWithIdDTO updateWorker(Integer id, WorkerDTO workerDTO) throws NoSuchWorkerException;

    WorkerWithIdDTO getWorkerById(Integer id) throws NoSuchWorkerException;

    void deleteWorkerById(Integer id) throws NoSuchWorkerException;

    List<WorkerWithIdDTO> getAllWorkers(String size, String page, String filter, String sort) throws BadRequestException;

    ValueDto getNumberOfWorkersWithBiggerEndDate(String endDate);

    ValueDto getNumberOfWorkersWithSmallerPosition(String position);

    List<WorkerWithIdDTO> getWorkersWithNameContains(String name) throws BadRequestException;

    WorkerWithIdDTO hireWorker(Integer workerId, Integer organizationId, String position, String startDate) throws NoSuchWorkerException;
}
