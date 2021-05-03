package com.example.scrumer.project.application.port;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface ProjectsUseCase {
    Optional<Project> findById(Long id);
    Project addProject(CreateProjectCommand command);
    void removeById(Long id);
    List<Project> findAll();
    void addTaskToProductBacklog(Long id, CreateTaskCommand command);
    void addTeamToProject(Long id, TeamCommand toCommand);

    @Value
    class CreateProjectCommand {
        String name;
        String accessCode;

        public Project toProject() {
            return new Project(name, accessCode);
        }
    }

    @Value
    class TeamCommand {
        String name;
        String accessCode;
    }
}
