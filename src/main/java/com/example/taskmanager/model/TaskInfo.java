package com.example.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskInfo {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Boolean completed;

}
