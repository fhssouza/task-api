package org.souzatech.tasks.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.souzatech.tasks.dto.request.TaskCreateRequest;
import org.souzatech.tasks.dto.response.TaskAllocateResponse;
import org.souzatech.tasks.dto.response.TaskResponse;
import org.souzatech.tasks.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@Tag(name = "Tarefas")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping("post/tarefas")
    public ResponseEntity<TaskResponse> created(@RequestBody TaskCreateRequest request, UriComponentsBuilder uriBuilder){
        TaskResponse response = service.create(request);
        return ResponseEntity
                .created(uriBuilder
                        .path("/post/tarefas/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @PutMapping("/put/tarefas/alocar/{id}")
    public ResponseEntity<TaskAllocateResponse> update(@PathVariable Long id, @RequestParam Long personId, UriComponentsBuilder uriBuilder){
        TaskAllocateResponse response = service.allocatePerson(id, personId);

        return ResponseEntity
                .created(uriBuilder
                        .path("/put/tarefas/alocar/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @PutMapping("/put/tarefas/finalizar/{id}")
    public ResponseEntity<TaskAllocateResponse> finish(@PathVariable Long id, UriComponentsBuilder uriBuilder){
        TaskAllocateResponse response = service.finish(id);

        return ResponseEntity
                .created(uriBuilder
                        .path("/put/tarefas/finalizar/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @GetMapping("/get/tarefas/pendentes")
    public ResponseEntity<List<TaskResponse>> findOldestUnallocatedTasks(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findOldestUnallocatedTasks());
    }

}
