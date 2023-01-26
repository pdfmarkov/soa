package com.markov.resources.impl;

import com.markov.entities.Worker;
import com.markov.entities.dto.ValueDto;
import com.markov.entities.dto.WorkerDTO;
import com.markov.entities.dto.WorkerWithIdDTO;
import com.markov.entities.enums.Position;
import com.markov.entities.mappers.WorkerMapper;
import com.markov.exceptions.BadRequestException;
import com.markov.exceptions.NoSuchWorkerException;
import com.markov.repositories.WorkerRepository;
import com.markov.resources.WorkerService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class WorkerServiceImpl implements WorkerService {

    @Inject
    private WorkerMapper workerMapper;

    @Inject
    private WorkerRepository workerRepository;

    @Override
    public WorkerWithIdDTO addNewWorker(WorkerDTO workerDTO) throws NoSuchWorkerException {
        return workerMapper.convertWorkerToWorkerWithIdDTO(
                workerRepository.save(
                        workerMapper.convertWorkerDTOtoWorker(
                                workerDTO)));
    }

    @Override
    public WorkerWithIdDTO updateWorker(Integer workerId, WorkerDTO workerDTO) throws NoSuchWorkerException {
        Worker worker = workerMapper.convertWorkerDTOtoWorker(workerDTO);
        worker.setId(workerId);
        return workerMapper.convertWorkerToWorkerWithIdDTO(
                workerRepository.update(worker));
    }

    @Override
    public WorkerWithIdDTO getWorkerById(Integer workerId) throws NoSuchWorkerException {
        return workerMapper.convertWorkerToWorkerWithIdDTO(
                workerRepository.get(workerId));
    }

    @Override
    public void deleteWorkerById(Integer workerId) throws NoSuchWorkerException {
        workerRepository.delete(workerId);
    }

    @Override
    public List<WorkerWithIdDTO> getAllWorkers(String size, String page, String filter, String sort) throws BadRequestException {

        int pageNumber = page == null ? 0 : Integer.parseInt(page);
        int itemsPerPage = size == null ? 10 : Integer.parseInt(size);

        List<String> filterParts = getFilterParams(filter);

        return workerRepository.getWithSortAndFilter(filterParts, sort, itemsPerPage, pageNumber).stream()
                .map(worker -> workerMapper.convertWorkerToWorkerWithIdDTO(worker)).collect(Collectors.toList());
    }

    @Override
    public ValueDto getNumberOfWorkersWithBiggerEndDate(String endDate) {
        return new ValueDto(workerRepository.getNumberOfWorkersWithBiggerEndDate(endDate));
    }

    @Override
    public ValueDto getNumberOfWorkersWithSmallerPosition(String position) {
        return new ValueDto(workerRepository.getNumberOfWorkersWithSmallerPosition(position));
    }

    @Override
    public List<WorkerWithIdDTO> getWorkersWithNameContains(String name) throws BadRequestException {
        return workerRepository.getWorkersWithNameContains(name).stream()
                .map(worker -> workerMapper.convertWorkerToWorkerWithIdDTO(worker)).collect(Collectors.toList());
    }

    @Override
    public WorkerWithIdDTO hireWorker(Integer workerId, Integer organizationId, String position, String startDate) throws NoSuchWorkerException {
        Worker worker = workerRepository.get(workerId);
        WorkerDTO workerDTO = workerMapper.convertWorkerToWorkerDTO(worker);
        workerDTO.getOrganization().setId(organizationId);
        workerDTO.setPosition(Position.valueOf(position.toUpperCase()));
        workerDTO.setStartDate(String.valueOf(ZonedDateTime.parse(startDate, DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z"))));
        return workerMapper.convertWorkerToWorkerWithIdDTO(
                workerRepository.update(workerMapper.convertWorkerDTOtoWorker(workerDTO)));
    }

    private List<String> getFilterParams(String filter) {
        List<String> filterParts = new ArrayList<>();
        if (filter != null) {
            if (filter.contains(";")) {
                String[] strs = filter.split(";");
                for (int i = 0; i < strs.length; i++) {
                    if (strs[i].contains("name=")) {
                        StringBuffer sb = new StringBuffer(strs[i]);
                        sb.insert(5, "'");
                        strs[i] = sb.toString() + "'";
                    }
                }
                filterParts = Arrays.asList(strs);
            } else {
                String param = filter;
                if (param.contains("name=")) {
                    StringBuffer sb = new StringBuffer(param);
                    sb.insert(5, "'");
                    param = sb.toString() + "'";
                }
                filterParts.add(param);
            }
        }
        return filterParts;
    }
}
