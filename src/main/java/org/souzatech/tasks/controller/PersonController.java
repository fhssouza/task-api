package org.souzatech.tasks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Adicionar uma pessoa",
            description = "<ul><li><p>Recurso utilizado para o cadastro de pessoas e seu departamento.<p></li>" +
                    "<li><p>Para Pessoa deve ser inserido nome.</p></li>" +
                    "<li><p>Para o departamento deve ser inserido o id referente o departamento.</p></li>" +
                    "<p><strong>OBS:</strong> Será feita consulta e validação se o departamento existe.</p></ul>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
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

    @Operation(
            summary = "Alterar uma pessoa",
            description = "<ul><li><p>Atualização dos dados referentes a uma pessoa através de seu ID.</p></li></ul>")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = PersonResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
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

    @Operation(
            summary = "Remover pessoa",
            description = "<ul><li><p>Exclui os dados referentes a uma pessoa através de seu ID.</p></li></ul>")
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
    })
    @DeleteMapping("/delete/pessoas/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(
            summary = "Listar pessoas por horas gastas",
            description = "<ul><li><p>Retorna a lista de pessoas trazendo nome, departamento, total de horas gastas nas tarefas.</p></li>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonDurationResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/get/pessoas")
    public ResponseEntity<List<PersonDurationResponse>> findPersonTaskDurations(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findPersonTaskDurations());
    }

    @Operation(
            summary = "Listar pessoas por media e horas gastas",
            description = "<ul><li><p>Retorna a lista de pessoas buscando por nome e período, retorna média de horas gastas por tarefa.</p></li>" +
                          "<p><strong>OBS:</strong> Será feita consulta pelo nome da pessoa e periodo no formato de data (<strong>yyyy-MM-dd</strong>).</p></ul>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonDurationResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/get/pessoas/gastos")
    public ResponseEntity<List<PersonDurationResponse>> findAverageTaskHoursByNameAndPeriod(@Param("name") String name, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAverageTaskHoursByNameAndPeriod(name, startDate, endDate));
    }

}
