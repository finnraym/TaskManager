package com.example.taskmanager.service.mapper;

import com.example.taskmanager.model.TaskInfo;
import com.example.taskmanager.model.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskInfoMapper {

    TaskInfo toInfo(Task task);
    Task toEntity(TaskInfo taskInfo);
    List<TaskInfo> toInfoList(List<Task> tasks);
}
