package com.example.scrumer.project.application.port;

import com.example.scrumer.task.domain.Task;

import java.util.List;

import static com.example.scrumer.task.application.port.TasksUseCase.*;

public interface ProductBacklogUseCase {
    void addTask(Long id, CreateTaskCommand command);

    List<Task> findProductBacklog(Long id);
}
