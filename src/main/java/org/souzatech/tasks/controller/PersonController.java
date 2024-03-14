package org.souzatech.tasks.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.souzatech.tasks.dto.request.PersonCreateRequest;
import org.souzatech.tasks.dto.response.PersonDurationResponse;
import org.souzatech.tasks.dto.response.PersonResponse;
import org.souzatech.tasks.services.PersonService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "Pessoas")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping("post/pessoas")
    public ResponseEntity<PersonResponse> created(@RequestBody PersonCreateRequest request, UriComponentsBuilder uriBuilder){
        PersonResponse response = service.create(request);
        return ResponseEntity
                .created(uriBuilder
                        .path("/post/pessoas/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @PutMapping("put/pessoas/{id}")
    public ResponseEntity<PersonResponse> update(@PathVariable Long id, @RequestBody PersonCreateRequest request, UriComponentsBuilder uriBuilder){
        PersonResponse response = service.update(id, request);

        return ResponseEntity
                .created(uriBuilder
                        .path("/post/pessoas/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @DeleteMapping("/delete/pessoas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/get/pessoas")
    public ResponseEntity<List<PersonDurationResponse>> findPersonTaskDurations(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findPersonTaskDurations());
    }

    @GetMapping("/get/pessoas/gastos")
    public ResponseEntity<List<PersonDurationResponse>> findAverageTaskHoursByNameAndPeriod(@Param("name") String name, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAverageTaskHoursByNameAndPeriod(name, startDate, endDate));
    }

}
