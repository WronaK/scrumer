package com.example.scrumer.task.service.useCase;

import com.example.scrumer.task.command.CreateTaskCommand;
import com.example.scrumer.task.command.SubtaskCommand;
import com.example.scrumer.task.command.TaskCommand;
import com.example.scrumer.task.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TaskUseCase {
    Optional<Task> findById(Long id);

    List<Task> findAll();

    void deleteById(Long id);

    void addSubtasks(Long id, SubtaskCommand command);

    void updateTask(TaskCommand task);
}
