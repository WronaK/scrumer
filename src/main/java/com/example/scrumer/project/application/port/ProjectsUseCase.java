package com.example.scrumer.project.application.port;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.project.request.UpdateProjectRequest;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectsUseCase {
    Optional<Project> findById(Long id);

    Project addProject(CreateProjectCommand command, String email);

    void removeById(Long id);

    List<Project> findAll();

    void updateProject(UpdateProjectCommand command);

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

    @Value
    class UpdateProjectCommand {
        Long id;
        String name;
        String accessCode;
        String description;
        String productOwner;
        String scrumMaster;
    }
}
