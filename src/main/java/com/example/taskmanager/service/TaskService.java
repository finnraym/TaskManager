package com.example.taskmanager.service;

import com.example.taskmanager.model.TaskInfo;

import java.util.List;

public interface TaskService {

    TaskInfo getById(Long id);

    List<TaskInfo> getAll();

    TaskInfo create(TaskInfo taskInfo);

    TaskInfo update(Long id, TaskInfo taskInfo);

    void delete(Long id);
}
