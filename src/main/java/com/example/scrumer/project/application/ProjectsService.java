package com.example.scrumer.project.application;

import com.example.scrumer.project.application.port.ProjectsUseCase;
import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import com.example.scrumer.user.db.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectsService implements ProjectsUseCase {
    private final ProjectJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final ProjectMembersService projects;

    @Override
    public Optional<Project> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Project addProject(CreateProjectCommand command, String email) {
        Project project = Project.builder()
                .name(command.getName())
                .description(command.getDescription())
                .accessCode(command.getAccessCode())
                .build();
        userRepository.findByEmail(email).ifPresent(project::setCreator);
        userRepository.findByEmail(command.getProductOwner()).ifPresent(project::setProductOwner);
        userRepository.findByEmail(command.getScrumMaster()).ifPresent(project::setScrumMaster);
        this.projects.addTeams(project, command.getTeams());
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

    @Override
    public void updateProject(UpdateProjectCommand command) {
        repository.findById(command.getId())
                .map(project -> {
                    this.updateFields(command, project);
                    return repository.save(project);
                });
    }

    private void updateFields(UpdateProjectCommand command, Project project) {
        if(command.getName() != null) {
            project.setName(command.getName());
        }

        if(command.getDescription() != null) {
            project.setDescription(command.getDescription());
        }

        if(command.getAccessCode() != null) {
            project.setAccessCode(command.getAccessCode());
        }

        if(command.getScrumMaster() != null) {
            userRepository.findByEmail(command.getScrumMaster())
                    .ifPresent(project::setScrumMaster);
        }

        if(command.getProductOwner() != null) {
            userRepository.findByEmail(command.getProductOwner())
                    .ifPresent(project::setProductOwner);
        }
    }

}

