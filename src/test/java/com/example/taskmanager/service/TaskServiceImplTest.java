package com.example.taskmanager.service;

import com.example.taskmanager.model.TaskInfo;
import com.example.taskmanager.model.entity.Task;
import com.example.taskmanager.model.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.mapper.TaskInfoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;
    @Mock
    private TaskRepository repository;
    @Mock
    private TaskInfoMapper mapper;

    private Task task1, task2, task3;
    private TaskInfo taskInfo1, taskInfo2, taskInfo3;

    @BeforeEach
    public void setUp() {
        task1 = new Task(1L,"some title 1", "some desc 1", LocalDate.now(), false);
        task2 = new Task(2L,"some title 2", "some desc 2", LocalDate.of(2024, Month.APRIL, 16), false);
        task3 = new Task(3L,"some title 3", "some desc 3", LocalDate.of(2024, Month.MAY, 6), false);

        taskInfo1 = new TaskInfo(task1.getId(), task1.getTitle(), task1.getDescription(), task1.getDueDate(), task1.isCompleted());
        taskInfo2 = new TaskInfo(task2.getId(), task2.getTitle(), task2.getDescription(), task2.getDueDate(), task2.isCompleted());
        taskInfo3 = new TaskInfo(task3.getId(), task3.getTitle(), task3.getDescription(), task3.getDueDate(), task3.isCompleted());
    }

    @Test
    void testGetAllTasks() {
        // given
        List<Task> expectedTasks = List.of(task1, task2, task3);
        List<TaskInfo> expectedInfos = List.of(taskInfo1, taskInfo2, taskInfo3);
        // when
        Mockito.when(repository.findAll()).thenReturn(expectedTasks);
        Mockito.when(mapper.toInfoList(expectedTasks)).thenReturn(expectedInfos);
        // then
        List<TaskInfo> result = taskService.getAll();
        assertEquals(expectedInfos.size(), result.size());
        assertIterableEquals(expectedInfos, result);
    }

    @Test
    void testGetTasksById_Success() {
        // given
        Optional<Task> expectedOptionalTask = Optional.of(task1);
        TaskInfo expectedTaskInfo = taskInfo1;
        // when
        Mockito.when(repository.findById(task1.getId())).thenReturn(expectedOptionalTask);
        Mockito.when(mapper.toInfo(task1)).thenReturn(expectedTaskInfo);
        // then
        TaskInfo result = taskService.getById(task1.getId());
        assertEquals(expectedTaskInfo.getTitle(), result.getTitle());
    }

    @Test
    void testGetTasksById_ThrowException() {
        // given
        long id = 5L;
        // when
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        // then
        assertThrows(TaskNotFoundException.class, () -> taskService.getById(id));
    }

    @Test
    void testCreateTask_Success() {
        // given
        TaskInfo createTask = taskInfo1;
        Task createEntity = task1;
        TaskInfo expectedTaskInfo = taskInfo1;
        // when
        Mockito.when(repository.save(createEntity)).thenReturn(createEntity);
        Mockito.when(mapper.toInfo(createEntity)).thenReturn(expectedTaskInfo);
        Mockito.when(mapper.toEntity(createTask)).thenReturn(createEntity);
        // then
        TaskInfo result = taskService.create(createTask);
        assertEquals(expectedTaskInfo, result);
    }

    @Test
    void testUpdateTask_Success() {
        // given
        long id = task1.getId();
        TaskInfo updateTask = taskInfo1;
        Task updateEntity = task1;
        TaskInfo expectedTaskInfo = taskInfo1;
        // when
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(updateEntity));
        Mockito.when(repository.save(updateEntity)).thenReturn(updateEntity);
        Mockito.when(mapper.toInfo(updateEntity)).thenReturn(expectedTaskInfo);
        // then
        TaskInfo result = taskService.update(id, updateTask);
        assertEquals(expectedTaskInfo, result);
    }

    @Test
    void testUpdateTask_ThrowException() {
        // given
        long id = 5L;
        // when
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        // then
        assertThrows(TaskNotFoundException.class, () -> taskService.update(id, taskInfo1));
    }

    @Test
    void testDeleteTask_ThrowException() {
        // given
        long id = 5L;
        // when
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        // then
        assertThrows(TaskNotFoundException.class, () -> taskService.delete(id));
    }
}