package com.example.taskmanager.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Task DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskDto(
        @Schema(description = "Task title") String title,
        @Schema(description = "Task description") String description,
        @Schema(description = "Task due date") @JsonFormat(pattern = "dd-MM-yyyy") LocalDate dueDate,
        @Schema(description = "Task is completed") Boolean completed) {
}
