package com.example.scrumer.task.application;

import com.example.scrumer.task.application.port.TasksUseCase;
import com.example.scrumer.task.db.SubtaskJpaRepository;
import com.example.scrumer.task.db.TaskDetailsJpaRepository;
import com.example.scrumer.task.db.TaskJpaRepository;
import com.example.scrumer.task.domain.Subtask;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.domain.TaskDetails;
import com.example.scrumer.task.request.TaskRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TasksService implements TasksUseCase {
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
    public void addSubtask(Long id, CreateTaskCommand command) {
        repository.findById(id)
                .ifPresent(task -> {
                    Subtask subtask = Subtask.builder()
                            .taskDetails(TaskDetails
                                    .builder()
                                    .title(command.getTitle())
                                    .description(command.getDescription())
                                    .priority(command.getPriority())
                                    .build())
                            .build();
                    task.addSubtask(subtask);
                    repository.save(task);
                });
    }

    @Override
    public void updateTask(TaskRequest task) {
        repository.findById(task.getId())
                .map(savedTask -> {
                    this.updateFields(task, savedTask);
                    return repository.save(savedTask);
                });
    }

    private void updateFields(TaskRequest task, Task savedTask) {
        if(task.getTitle() != null) {
            savedTask.getTaskDetails().setTitle(task.getTitle());
        }

        if(task.getDescription() != null) {
            savedTask.getTaskDetails().setDescription(task.getDescription());
        }

        if(task.getPriority() != null) {
            savedTask.getTaskDetails().setPriority(task.getPriority());
        }

        if(task.getStoryPoints() != null) {
            savedTask.getTaskDetails().setStoryPoints(task.getStoryPoints());
        }
    }
}

