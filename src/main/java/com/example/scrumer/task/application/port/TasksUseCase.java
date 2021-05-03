package com.example.scrumer.task.application.port;

import com.example.scrumer.task.domain.Task;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface TasksUseCase {
    Optional<Task> findById(Long id);

    List<Task> findAll();

    void deleteById(Long id);

    void addSubtask(Long id, CreateTaskCommand toCreateCommand);

    @Value
    class CreateTaskCommand {
        String title;
        String description;
        Integer priority;

        public Task toTask() {
            return new Task(title, description, priority);
        }
    }
}
