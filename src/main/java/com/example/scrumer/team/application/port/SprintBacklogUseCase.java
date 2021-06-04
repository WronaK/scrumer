package com.example.scrumer.team.application.port;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.domain.Task;
import lombok.Value;

import java.util.List;

public interface SprintBacklogUseCase {
    List<Task> getSprintBacklog(Long id);

    List<Project> findProjectsById(Long id);

    void addProject(Long id, ProjectCommand command);

    void addTask(Long id, Long idTask);

    @Value
    class ProjectCommand {
        String name;
        String accessCode;
    }
}
