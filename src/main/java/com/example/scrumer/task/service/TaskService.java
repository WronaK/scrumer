package com.example.scrumer.task.service;

import com.example.scrumer.task.command.CreateTaskCommand;
import com.example.scrumer.task.command.SubtaskCommand;
import com.example.scrumer.task.entity.*;
import com.example.scrumer.task.service.useCase.TaskUseCase;
import com.example.scrumer.task.repository.TaskJpaRepository;
import com.example.scrumer.task.command.TaskCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class TaskService implements TaskUseCase {
    private final TaskJpaRepository repository;

    @Override
    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void addSubtasks(Long id, SubtaskCommand command) {
        repository.findById(id)
                .ifPresent(task -> {
                    for(CreateTaskCommand command1: command.getTasks()) {
                        this.addSubtask(task, command1);
                    }
                    repository.save(task);
                });
    }

    private void addSubtask(Task task, CreateTaskCommand command) {
        Subtask subtask = Subtask.builder()
                .taskDetails(TaskDetails
                        .builder()
                        .title(command.getTitle())
                        .description(command.getDescription())
                        .priority(PriorityStatus.valueOf(command.getPriority()))
                        .build())
                .statusTask(StatusTask.NEW_TASK)
                .build();
        task.addSubtask(subtask);
    }

    @Override
    public void updateTask(TaskCommand task) {
        repository.findById(task.getId())
                .map(savedTask -> {
                    this.updateFields(task, savedTask);
                    return repository.save(savedTask);
                });
    }

    private void updateFields(TaskCommand task, Task savedTask) {
        if(task.getTitle() != null) {
            savedTask.getTaskDetails().setTitle(task.getTitle());
        }

        if(task.getDescription() != null) {
            savedTask.getTaskDetails().setDescription(task.getDescription());
        }

        if(task.getPriority() != null) {
            savedTask.getTaskDetails().setPriority(PriorityStatus.valueOf(task.getPriority()));
        }

        if(task.getStoryPoints() != null) {
            savedTask.getTaskDetails().setStoryPoints(task.getStoryPoints());
        }
    }
}

