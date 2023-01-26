package com.markov.service2.controller;

import com.markov.service2.entity.WorkerWithIdDTO;
import com.markov.service2.service.HRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hr")
public class HRController {
    private final HRService hrService;

    @Autowired
    public HRController(HRService hrService) {
        this.hrService = hrService;
    }

    @CrossOrigin(origins = "https://localhost:44228")
    @GetMapping("/hire/{person-id}/{org-id}/{position}/{start-date}")
    public ResponseEntity<WorkerWithIdDTO> hirePerson(@PathVariable("person-id") Integer personId,
                                                                @PathVariable("org-id") Integer organizationId,
                                                                @PathVariable("position") String position,
                                                                @PathVariable("start-date") String startDate) {
        return new ResponseEntity<WorkerWithIdDTO>(hrService.hirePerson(personId, organizationId, position, startDate),
                HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://localhost:44228")
    @GetMapping("/fire/{person-id}")
    public ResponseEntity<String> firePerson(@PathVariable("person-id") Integer personId) {
        return new ResponseEntity<String>(hrService.firePerson(personId), HttpStatus.OK);
    }
}
