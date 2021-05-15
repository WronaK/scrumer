package com.example.scrumer.project.application;

import com.example.scrumer.project.application.port.ProjectsUseCase;
import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.db.TaskDetailsJpaRepository;
import com.example.scrumer.task.db.TaskJpaRepository;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.domain.TaskDetails;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.team.db.TeamJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectsService implements ProjectsUseCase {
    private final ProjectJpaRepository repository;
    private final TaskJpaRepository taskRepository;
    private final UserJpaRepository userRepository;
    private final TaskDetailsJpaRepository taskDetailsRepository;
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
        userRepository.findByEmail(email).ifPresent(project::addCreator);
        userRepository.findByEmail(command.getProductOwner()).ifPresent(project::setProductOwner);
        userRepository.findByEmail(command.getScrumMaster()).ifPresent(project::setScrumMaster);
        project = repository.save(project);
        this.addTeams(command.getTeams(), project);
        return repository.save(project);
    }

    private void addTeams(List<TeamCommand> commands, Project project) {
        for (TeamCommand command: commands) {
            this.addTeamToProject(project.getId(), command);
        }
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
                    TaskDetails taskDetails = taskDetailsRepository
                            .save(TaskDetails.builder()
                                    .title(command.getTitle())
                                    .description( command.getDescription())
                                    .priority(command.getPriority())
                                    .build());
                    Task task = taskRepository.save(Task.builder()
                            .taskDetails(taskDetails)
                            .build());
                    project.addTaskToProductBacklog(task);
                    repository.save(project);
                });
    }

    @Override
    public List<Task> getProductBacklog(Long id) {
        return repository.getProductBacklog(id);
    }

    @Override
    public void addTeamToProject(Long id, TeamCommand command) {
        repository.findById(id)
                .ifPresent(project -> {
                    teamRepository.findTeamByNameAndAccessCode(command.getName(), command.getAccessCode()).ifPresent(
                            team -> {
                                team.addProject(project);
                                teamRepository.save(team);
                                project.addTeam(team);
                                repository.save(project);
                            }
                    );
                });
    }
}

