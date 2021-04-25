package com.example.scrumer.project.application.port;

import com.example.scrumer.project.domain.Project;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface ProjectsUseCase {
    Optional<Project> findById(Long id);
    Project addProject(CreateProjectCommand command);
    void removeById(Long id);
    List<Project> findAll();

    @Value
    class CreateProjectCommand {
        String name;
        String accessCode;

        public Project toProject() {
            return new Project(name, accessCode);
        }
    }
}
