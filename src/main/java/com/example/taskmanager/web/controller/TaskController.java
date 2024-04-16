package com.example.taskmanager.web.controller;

import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.web.dto.TaskDto;
import com.example.taskmanager.web.mapper.TaskDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task manager controller", description = "Task manager application APIs")
public class TaskController {

    private final TaskService taskService;
    private final TaskDtoMapper dtoMapper;

    @Operation(
            summary = "Get task",
            description = "Getting task by id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = TaskDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")})
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                dtoMapper.infoToDto(taskService.getById(id))
        );
    }

    @Operation(
            summary = "Get all tasks",
            description = "Getting all tasks."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = TaskDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")})
    })
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAll() {
        return ResponseEntity.ok(
                dtoMapper.infoListToDto(taskService.getAll())
        );
    }

    @Operation(
            summary = "Create task",
            description = "Creating new task."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = TaskDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestBody TaskDto taskDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dtoMapper.infoToDto(
                        taskService.create(dtoMapper.dtoToInfo(taskDto))
                ));
    }

    @Operation(
            summary = "Update task",
            description = "Updating existing task."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = TaskDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")})
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(dtoMapper.infoToDto(
                        taskService.update(id, dtoMapper.dtoToInfo(taskDto))
                ));
    }

    @Operation(
            summary = "Delete task",
            description = "Deleting existing task."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema =
                    @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema =
                    @Schema(implementation = String.class, description = "Error message"), mediaType = "application/json")})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.ok("Delete task with id " + id + " successfully completed!");
    }
}
