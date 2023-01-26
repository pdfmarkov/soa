package com.markov.repositories;

import com.markov.entities.Worker;
import com.markov.entities.enums.Position;
import com.markov.exceptions.BadRequestException;
import com.markov.exceptions.NoSuchWorkerException;

import java.util.List;

public interface WorkerRepository {

    Worker save(Worker worker) throws NoSuchWorkerException;

    Worker get(Integer id) throws NoSuchWorkerException;

    Worker update(Worker worker) throws NoSuchWorkerException;

    void delete(Integer id) throws NoSuchWorkerException;

    List<Worker> getWithSortAndFilter(List<String> filterParams, String sortParam,
                                      int itemsPerPage, int pageNumber) throws BadRequestException;

    Long getNumberOfWorkersWithBiggerEndDate(String endDate);

    Long getNumberOfWorkersWithSmallerPosition(String position);

    List<Worker> getWorkersWithNameContains(String name) throws BadRequestException;
}
