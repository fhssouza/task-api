package org.souzatech.tasks.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.souzatech.tasks.dto.request.DepartmentCreateRequest;
import org.souzatech.tasks.dto.response.DepartmentAllocateResponse;
import org.souzatech.tasks.dto.response.DepartmentResponse;
import org.souzatech.tasks.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@Tag(name = "Departamentos")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping("get/departamentos")
    public ResponseEntity<List<DepartmentAllocateResponse>> getDepartmentAllocationStatistics(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getDepartmentAllocationStatistics());
    }

    @PostMapping("post/departamentos")
    public ResponseEntity<DepartmentResponse> created(@RequestBody DepartmentCreateRequest request, UriComponentsBuilder uriBuilder){
        DepartmentResponse response = service.create(request);
        return ResponseEntity
                .created(uriBuilder
                        .path("/departamentos/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @PutMapping("put/departamentos/{id}")
    public ResponseEntity<DepartmentResponse> update(@PathVariable Long id, @RequestBody DepartmentCreateRequest request, UriComponentsBuilder uriBuilder){
        DepartmentResponse response = service.update(id, request);

        return ResponseEntity
                .created(uriBuilder
                        .path("/departamentos/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @DeleteMapping("delete/departamentos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
