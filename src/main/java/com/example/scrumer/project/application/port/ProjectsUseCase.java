package com.example.scrumer.project.application.port;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.domain.Task;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectsUseCase {
    Optional<Project> findById(Long id);
    Project addProject(CreateProjectCommand command, String email);
    void removeById(Long id);
    List<Project> findAll();
    void addTaskToProductBacklog(Long id, CreateTaskCommand command);
    void addTeamToProject(Long id, TeamCommand toCommand);

    List<Task> getProductBacklog(Long id);

    @Value
    class CreateProjectCommand {
        String name;
        String accessCode;
        String description;
        String productOwner;
        String scrumMaster;
        Set<TeamCommand> teams;
    }

    @Value
    class TeamCommand {
        String name;
        String accessCode;
    }
}
