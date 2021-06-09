package com.example.scrumer.project.application;

import com.example.scrumer.project.application.port.ProjectsUseCase;
import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import com.example.scrumer.project.request.UpdateProjectRequest;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.domain.StatusTask;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.domain.TaskDetails;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.team.db.TeamJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    public void addTeamToProject(Long id, Set<TeamCommand> command) {
        repository.findById(id).ifPresent(project -> {
           this.addTeams(project, command);
           repository.save(project);
        });
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
                            .statusTask(StatusTask.NEW)
                            .build();
                    project.addTaskToProductBacklog(task);
                    repository.save(project);
                });
    }

    @Override
    public List<Task> getProductBacklog(Long id) {
        return repository.getProductBacklog(id);
    }

    @Override
    public void updateProject(UpdateProjectRequest project) {
        repository.findById(project.getId())
                .map(savedProject -> {
                    this.updateFields(project, savedProject);
                    return repository.save(savedProject);
                });
    }

    @Override
    public void removeTeamWithProject(Long id, Long idTeam) {
        repository.findById(id).ifPresent(project ->
                teamRepository.findById(idTeam)
                        .ifPresent(team -> {
                            project.removeTeam(team);
                            repository.save(project);
                        }));
    }

    @Override
    public List<Project> findByTeamId(Long id) {
        return repository.findByTeamId(id);
    }

    @Override
    public List<Team> findTeamsByProjectId(Long id) {
        return repository.findTeamsByProjectId(id);
    }

    private void updateFields(UpdateProjectRequest project, Project savedProject) {
        if(project.getName() != null) {
            savedProject.setName(project.getName());
        }

        if(project.getDescription() != null) {
            savedProject.setDescription(project.getDescription());
        }

        if(project.getAccessCode() != null) {
            savedProject.setAccessCode(project.getAccessCode());
        }

        if(project.getScrumMaster() != null) {
            userRepository.findByEmail(project.getScrumMaster())
                    .ifPresent(savedProject::setScrumMaster);
        }

        if(project.getProductOwner() != null) {
            userRepository.findByEmail(project.getProductOwner())
                    .ifPresent(savedProject::setProductOwner);
        }
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

