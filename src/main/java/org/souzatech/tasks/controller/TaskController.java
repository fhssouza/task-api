package org.souzatech.tasks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Adicionar uma tarefa",
            description = "<ul><li><p>Recurso utilizado para o cadastro de uma tarefa, vinculado ao departamento.<p></li>" +
                    "<li><p>Para Tarefa deve ser inserido o titulo.</p></li>" +
                    "<li><p>Para Tarefa deve ser inserido a descrição.</p></li>" +
                    "<li><p>Para Tarefa deve ser inserido a data referente o prazo no formato (<strong>yyyy-MM-dd</strong>).</p></li>" +
                    "<li><p>Para Tarefa deve ser inserido o ID referente o departamento.</p></li>" +
                    "<li><p>Para Tarefa deve ser inserido a duração.</p></li>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TaskResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
    })
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

    @Operation(
            summary = "Alocar uma pessoa",
            description = "<ul><li><p>Alocar uma pessoa na tarefa que tenha o mesmo departamento através do ID da tarefa e ID da pessoa.</p></li></ul>"+
                    "<p><strong>OBS:</strong> Será feita a consulta e validação se a pessoa está vinculada ao departamento da tarefa.</p></ul>")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = TaskAllocateResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
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

    @Operation(
            summary = "Finalizar a tarefa",
            description = "<ul><li><p>Finalizar a tarefa através de seu ID.</p></li></ul>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TaskAllocateResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
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

    @Operation(
            summary = "Lista de tarefas pendentes",
            description = "<ul><li><p>Retorna a lista de 03 tarefas que estejam sem pessoa alocada com os prazos mais antigos</p></li>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TaskResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/get/tarefas/pendentes")
    public ResponseEntity<List<TaskResponse>> findOldestUnallocatedTasks(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findOldestUnallocatedTasks());
    }

}
