package com.example.taskmanager.web.mapper;

import com.example.taskmanager.model.TaskInfo;
import com.example.taskmanager.web.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskDtoMapper {

    TaskDto infoToDto(TaskInfo info);

    @Mapping(target = "id", ignore = true)
    TaskInfo dtoToInfo(TaskDto dto);

    List<TaskDto> infoListToDto(List<TaskInfo> infos);

}
