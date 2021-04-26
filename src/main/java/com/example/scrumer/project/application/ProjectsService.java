package com.example.scrumer.project.application;

import com.example.scrumer.project.application.port.ProjectsUseCase;
import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.db.TaskJpaRepository;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.team.domain.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectsService implements ProjectsUseCase {
    private final ProjectJpaRepository repository;
    private final TaskJpaRepository taskRepository;
    private final TeamJpaRepository teamRepository;

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

    @Override
    public void addTaskToProductBacklog(Long id, CreateTaskCommand command) {
        repository.findById(id)
                .ifPresent(project -> {
                    Task task = taskRepository.save(Task.builder()
                            .title(command.getTitle())
                            .description(command.getDescription())
                            .priority(command.getPriority())
                            .build());
                    project.addTaskToProductBacklog(task);
                    repository.save(project);
                });
    }

    @Override
    public void addTeamToProject(Long id, TeamCommand command) {
        repository.findById(id)
                .ifPresent(project -> {
                    Team team = teamRepository.findByNameAndAccessCode(command.getName(), command.getAccessCode());
                    team.addProject(project);
                    teamRepository.save(team);
                    project.addTeam(team);
                    repository.save(project);
                });
    }
}

