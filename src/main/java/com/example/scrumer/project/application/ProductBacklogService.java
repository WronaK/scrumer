package com.example.scrumer.project.application;

import com.example.scrumer.project.application.port.ProductBacklogUseCase;
import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.domain.StatusTask;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.domain.TaskDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductBacklogService implements ProductBacklogUseCase {
    private final ProjectJpaRepository repository;

    @Override
    public void addTask(Long id, CreateTaskCommand command) {
        repository.findById(id)
                .ifPresent(project -> {
                    Task task = Task.builder()
                            .taskDetails(TaskDetails
                                    .builder()
                                    .title(command.getTitle())
                                    .description(command.getDescription())
                                    .priority(command.getPriority())
                                    .build())
                            .statusTask(StatusTask.NEW)
                            .build();
                    project.addTask(task);
                    repository.save(project);
                });
    }

    @Override
    public List<Task> findProductBacklog(Long id) {
        return repository.findProductBacklog(id);
    }
}
