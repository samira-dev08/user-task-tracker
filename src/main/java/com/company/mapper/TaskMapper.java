package com.company.mapper;

import com.company.dto.request.CategoryRequest;
import com.company.dto.request.TaskRequest;
import com.company.dto.response.TaskResponse;
import com.company.entity.Category;
import com.company.entity.Task;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toTask(TaskRequest taskRequest);
    @Mapping(source = "name",target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "description",target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "priority",target = "priority", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "deadline",target = "deadline", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "status",target = "status", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task updateTask(@MappingTarget Task task, TaskRequest request);
    TaskResponse toTaskResponse(Task task);
    Category toCategory(CategoryRequest categoryRequest);
}
