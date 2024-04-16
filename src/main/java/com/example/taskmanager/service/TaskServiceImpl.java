package com.example.taskmanager.service;

import com.example.taskmanager.model.TaskInfo;
import com.example.taskmanager.model.entity.Task;
import com.example.taskmanager.model.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.mapper.TaskInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.*;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final TaskInfoMapper infoMapper;
    @Override
    @Transactional(readOnly = true)
    public TaskInfo getById(Long id) {
        return infoMapper.toInfo(repository.findById(id)
                .orElseThrow(TaskNotFoundException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskInfo> getAll() {
        return infoMapper.toInfoList(repository.findAll());
    }

    @Override
    public TaskInfo create(TaskInfo taskInfo) {
        return infoMapper.toInfo(repository.save(infoMapper.toEntity(taskInfo)));
    }

    @Override
    public TaskInfo update(Long id, TaskInfo taskInfo) {
        Task task = repository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        task.setTitle(requireNonNullElse(taskInfo.getTitle(), task.getTitle()));
        task.setDescription(requireNonNullElse(taskInfo.getDescription(), task.getDescription()));
        task.setDueDate(requireNonNullElse(taskInfo.getDueDate(), task.getDueDate()));
        task.setCompleted(requireNonNullElse(taskInfo.getCompleted(), task.isCompleted()));
        return infoMapper.toInfo(repository.save(task));
    }

    @Override
    public void delete(Long id) {
        repository.delete(repository.findById(id)
                .orElseThrow(TaskNotFoundException::new));
    }
}
