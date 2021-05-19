package com.example.scrumer.project.application;

import com.example.scrumer.project.application.port.ProjectsUseCase;
import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.domain.TaskDetails;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.team.db.TeamJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProjectsService implements ProjectsUseCase {
    private final ProjectJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final TeamJpaRepository teamRepository;

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
        this.addTeams(project, command.getTeams());
        return repository.save(project);
    }

    @Override
    public void addTeamToProject(Long id, TeamCommand command) {
        repository.findById(id).ifPresent(project -> this.addTeam(project, command));
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
                    Task task = Task.builder()
                            .taskDetails(TaskDetails
                                    .builder()
                                    .title(command.getTitle())
                                    .description(command.getDescription())
                                    .priority(command.getPriority())
                                    .build())
                            .build();
                    project.addTaskToProductBacklog(task);
                    repository.save(project);
                });
    }

    @Override
    public List<Task> getProductBacklog(Long id) {
        return repository.getProductBacklog(id);
    }

    private void addTeams(Project project, Set<TeamCommand> commands) {
        for (TeamCommand command: commands) {
            this.addTeam(project, command);
        }
    }

    private void addTeam(Project project, TeamCommand command) {
        teamRepository.findTeamByNameAndAccessCode(command.getName(), command.getAccessCode())
                .ifPresent(project::addTeam);
    }
}

