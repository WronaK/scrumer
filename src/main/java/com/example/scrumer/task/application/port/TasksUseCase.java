package com.example.scrumer.task.application.port;

import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.request.TaskRequest;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface TasksUseCase {
    Optional<Task> findById(Long id);

    List<Task> findAll();

    void deleteById(Long id);

    void addSubtask(Long id, CreateTaskCommand toCreateCommand);

    void updateTask(TaskRequest task);

    @Value
    class CreateTaskCommand {
        String title;
        String description;
        Integer priority;
    }
}
