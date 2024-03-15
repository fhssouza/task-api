package org.souzatech.tasks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Listar departamentos",
            description = "<ul><li><p>Retorna a lista de departamentos e quantidade de pessoas e tarefas</p></li>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = DepartmentAllocateResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("get/departamentos")
    public ResponseEntity<List<DepartmentAllocateResponse>> getDepartmentAllocationStatistics(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getDepartmentAllocationStatistics());
    }

    @Operation(
            summary = "Adicionar departamentos",
            description = "<ul><li><p>Recurso para cadastrar departamentos</p></li>")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = DepartmentResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
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

    @Operation(
            summary = "Alterar departamentos",
            description = "<ul><li><p>Recurso para atualizar os departamentos por ID</p></li>")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = DepartmentResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
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

    @Operation(
            summary = "Remover departamentos",
            description = "<ul><li><p>Recurso para excluir o departamento por ID</p></li>")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("delete/departamentos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
