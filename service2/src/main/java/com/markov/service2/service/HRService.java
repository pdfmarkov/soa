package com.markov.service2.service;

import com.markov.service2.entity.OrganizationWithIdDTO;
import com.markov.service2.entity.PersonDTO;
import com.markov.service2.entity.WorkerDTO;
import com.markov.service2.entity.WorkerWithIdDTO;
import com.markov.service2.entity.enums.Position;
import com.markov.service2.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.crypto.spec.PSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@CrossOrigin
public class HRService {
    private final WebClient localApiClient;
    private final DateTimeFormatter startDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");

    @Autowired
    public HRService(WebClient localApiClient) {
        this.localApiClient = localApiClient;
    }

    @CrossOrigin
    public WorkerWithIdDTO hirePerson(Integer personId, Integer organizationId, String position, String startDate) {
        WorkerWithIdDTO worker = getWorkerFromMainService(personId);
        worker.getOrganization().setId(organizationId);
        worker.setPosition(Position.valueOf(position.toUpperCase()));
        worker.setStartDate(startDateFormatter.format(LocalDateTime.parse(startDate + ":00",
                DateTimeFormatter.ISO_DATE_TIME).atZone(ZoneId.systemDefault())));
        return updateWorkerFromMainService(personId, worker);
    }

    @CrossOrigin
    public String firePerson(Integer personId) {
        return deleteWorkerFromMainService(personId);
    }

    @CrossOrigin
    private WorkerWithIdDTO getWorkerFromMainService(Integer personId) {
        WorkerWithIdDTO responseBody = localApiClient.get()
                .uri("/workers/" + personId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(DataNotFoundException::new))
                .bodyToMono(WorkerWithIdDTO.class)
                .block();
        if (responseBody == null)
            throw new DataNotFoundException("Workers not found");
        return responseBody;
    }

    @CrossOrigin
    private WorkerWithIdDTO updateWorkerFromMainService(Integer personId, WorkerWithIdDTO worker) {
        WorkerWithIdDTO responseBody = localApiClient.put()
                .uri("/workers/" + personId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createJsonFromWorker(worker)))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(DataNotFoundException::new))
                .bodyToMono(WorkerWithIdDTO.class)
                .block();
        if (responseBody == null)
            throw new DataNotFoundException("Workers not found");
        return responseBody;
    }

    @CrossOrigin
    private String deleteWorkerFromMainService(Integer personId) {
        return localApiClient.delete()
                .uri("/workers/" + personId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(DataNotFoundException::new))
                .bodyToMono(String.class)
                .block();
    }

    private String createJsonFromWorker(WorkerWithIdDTO worker){
        return  "{\n" +
                "  \"coordinates\": {\n" +
                "    \"x\": " + worker.getCoordinates().getX() + ",\n" +
                "    \"y\": " + worker.getCoordinates().getY() + "\n" +
                "  },\n" +
                "  \"endDate\": \"" + worker.getEndDate() + "\",\n" +
                "  \"name\": \"" + worker.getName() + "\",\n" +
                "  \"salary\": " + worker.getSalary() + ",\n" +
                "  \"person\": {\n" +
                "    \"eyeColor\": \"" + worker.getPerson().getEyeColor() + "\",\n" +
                "    \"height\": " + worker.getPerson().getHeight() + ",\n" +
                "    \"location\": {\n" +
                "      \"name\": \"" + worker.getPerson().getLocation().getName() + "\",\n" +
                "      \"x\": " + worker.getPerson().getLocation().getX() + ",\n" +
                "      \"y\": " + worker.getPerson().getLocation().getY() + ",\n" +
                "      \"z\": " + worker.getPerson().getLocation().getZ() + "\n" +
                "    },\n" +
                "    \"passportID\": \"" + worker.getPerson().getPassportID() +  "\"\n" +
                "  },\n" +
                "  \"organization\": {\n" +
                "      \"id\":" + worker.getOrganization().getId() + "\n" +
                "  },\n" +
                "  \"position\": \"" + worker.getPosition() + "\",\n" +
                "  \"startDate\": \""  + worker.getStartDate() +  "\"\n" +
                "}";
    }
}
