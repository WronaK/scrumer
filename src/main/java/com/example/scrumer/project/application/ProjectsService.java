package com.example.scrumer.project.application;

import com.example.scrumer.project.application.port.ProjectsUseCase;
import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectsService implements ProjectsUseCase {
    private final ProjectJpaRepository repository;

    @Override
    public Optional<Project> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Project addProject(CreateProjectCommand command) {
        Project project = command.toProject();
        return repository.save(project);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Project> findAll() {
        return repository.findAll();
    }
}

