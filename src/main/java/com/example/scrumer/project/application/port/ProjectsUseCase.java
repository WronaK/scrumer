package com.example.scrumer.project.application.port;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.security.UserPrincipal;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.domain.Task;
import lombok.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

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

        public Project toProject() {
            return new Project(name, accessCode, description);
        }
    }

    @Value
    class TeamCommand {
        String name;
        String accessCode;
    }
}
