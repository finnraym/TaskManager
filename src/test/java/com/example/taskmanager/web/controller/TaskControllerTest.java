package com.example.taskmanager.web.controller;

import com.example.taskmanager.model.TaskInfo;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.web.dto.TaskDto;
import com.example.taskmanager.web.mapper.TaskDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TaskService taskService;
    @MockBean
    private TaskDtoMapper mapper;

    private TaskInfo taskInfo1, taskInfo2, taskInfo3;
    private TaskDto taskDto1, taskDto2, taskDto3;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void setUp() {
        taskInfo1 = new TaskInfo(1L,"some title 1", "some desc 1", LocalDate.now(), false);
        taskInfo2 = new TaskInfo(2L,"some title 2", "some desc 2", LocalDate.of(2024, Month.APRIL, 16), false);
        taskInfo3 = new TaskInfo(3L,"some title 3", "some desc 3", LocalDate.of(2024, Month.MAY, 6), false);

        taskDto1 = new TaskDto(taskInfo1.getTitle(), taskInfo1.getDescription(), taskInfo1.getDueDate(), taskInfo1.getCompleted());
        taskDto2 = new TaskDto(taskInfo2.getTitle(), taskInfo2.getDescription(), taskInfo2.getDueDate(), taskInfo2.getCompleted());
        taskDto3 = new TaskDto(taskInfo3.getTitle(), taskInfo3.getDescription(), taskInfo3.getDueDate(), taskInfo3.getCompleted());
    }
    @Test
    void testGetAll_ShouldReturnValidTaskDto() throws Exception {
        // given
        List<TaskDto> expectedDtos = List.of(taskDto1, taskDto2, taskDto3);
        List<TaskInfo> expectedTaskInfos = List.of(taskInfo1, taskInfo2, taskInfo3);
        // when
        Mockito.when(taskService.getAll()).thenReturn(expectedTaskInfos);
        Mockito.when(mapper.infoListToDto(expectedTaskInfos)).thenReturn(expectedDtos);
        // then
        mvc.perform(get("/api/v1/tasks").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDtos)));
    }

    @Test
    void testGetById_ShouldReturnValidTaskDto() throws Exception {
        // given
        TaskDto expectedDto = taskDto1;
        TaskInfo expectedTaskInfo = taskInfo1;
        // when
        Mockito.when(taskService.getById(expectedTaskInfo.getId())).thenReturn(expectedTaskInfo);
        Mockito.when(mapper.infoToDto(expectedTaskInfo)).thenReturn(expectedDto);
        // then
        mvc.perform(get("/api/v1/tasks/" + expectedTaskInfo.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void testCreate_ShouldReturnValidTaskDto() throws Exception {
        // given
        TaskDto expectedDto = taskDto1;
        TaskInfo expectedTaskInfo = taskInfo1;
        // when
        Mockito.when(taskService.create(expectedTaskInfo)).thenReturn(expectedTaskInfo);
        Mockito.when(mapper.infoToDto(expectedTaskInfo)).thenReturn(expectedDto);
        Mockito.when(mapper.dtoToInfo(expectedDto)).thenReturn(expectedTaskInfo);
        // then
        mvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void testUpdate_ShouldReturnValidTaskDto() throws Exception {
        // given
        TaskDto expectedDto = taskDto1;
        TaskInfo expectedTaskInfo = taskInfo1;
        // when
        Mockito.when(taskService.update(expectedTaskInfo.getId(), expectedTaskInfo)).thenReturn(expectedTaskInfo);
        Mockito.when(mapper.infoToDto(expectedTaskInfo)).thenReturn(expectedDto);
        Mockito.when(mapper.dtoToInfo(expectedDto)).thenReturn(expectedTaskInfo);
        // then
        mvc.perform(put("/api/v1/tasks/" + expectedTaskInfo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void testDelete_ShouldReturnValidMessage() throws Exception {
        // given
        long id = taskInfo1.getId();
        // when
        mvc.perform(delete("/api/v1/tasks/" + id))
        // then
                .andExpect(status().isOk())
                .andExpect(content().string("Delete task with id " + id + " successfully completed!"));

        Mockito.verify(taskService, Mockito.times(1)).delete(id);
    }

}