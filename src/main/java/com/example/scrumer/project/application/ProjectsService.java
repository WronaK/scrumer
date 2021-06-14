package com.example.scrumer.project.application;

import com.example.scrumer.project.application.port.ProjectsUseCase;
import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import com.example.scrumer.security.ValidatorPermission;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.domain.PriorityStatus;
import com.example.scrumer.task.domain.StatusTask;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.domain.TaskDetails;
import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.user.db.UserJpaRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProjectsService implements ProjectsUseCase {
    private final ProjectJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final ValidatorPermission validatorPermission;
    private final TeamJpaRepository teamsRepository;

    @Override
    public Optional<Project> findById(Long id) throws IllegalAccessException, NotFoundException {
        Optional<Project> project = repository.findById(id);
        this.validatorPermission.validateProjectPermission(project, this.getUserEmail());
        return project;
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
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Project> findAll() {
        return repository.findAll();
    }

    @Override
    public void updateProject(UpdateProjectCommand command) throws NotFoundException, IllegalAccessException {
        Optional<Project> project = repository.findById(command.getId());
        validatorPermission.validateModifyProjectPermission(project, this.getUserEmail());

        Project savedProject = project.get();
        this.updateFields(command, savedProject);
        repository.save(savedProject);

    }

    @Override
    public List<Project> findByUser(String userEmail) {
        return repository.findProjectByUser(userEmail);
    }

    @Override
    public void addTask(Long id, CreateTaskCommand command) throws NotFoundException, IllegalAccessException {
        Optional<Project> project = repository.findById(id);
        validatorPermission.validateModifyProjectPermission(project, this.getUserEmail());

        Project savedProject = project.get();
        Task task = Task.builder()
                .taskDetails(TaskDetails
                        .builder()
                        .title(command.getTitle())
                        .description(command.getDescription())
                        .priority(PriorityStatus.valueOf(command.getPriority()))
                        .build())
                .statusTask(StatusTask.NEW)
                .build();
        savedProject.addTask(task);
        repository.save(savedProject);
    }

    @Override
    public void addTeams(Long id, Set<TeamCommand> command) throws NotFoundException, IllegalAccessException {
        Optional<Project> project = repository.findById(id);
        this.validatorPermission.validateModifyProjectPermission(project, this.getUserEmail());

        Project savedProject = project.get();
        this.addTeams(savedProject, command);
        repository.save(savedProject);
    }

    @Override
    public void removeTeam(Long id, Long idTeam) throws NotFoundException, IllegalAccessException {
        Optional<Project> project = repository.findById(id);
        this.validatorPermission.validateModifyProjectPermission(project, this.getUserEmail());

        Project savedProject = project.get();
        this.teamsRepository.findById(idTeam).ifPresent(team -> {
            savedProject.removeTeam(team);
            repository.save(savedProject);
        });
    }

    private void addTeams(Project project, Set<TeamCommand> commands) {
        for (TeamCommand command: commands) {
            this.addTeam(project, command);
        }
    }

    private void addTeam(Project project, TeamCommand command) {
        teamsRepository.findTeamByNameAndAccessCode(command.getName(), command.getAccessCode())
                .ifPresent(project::addTeam);
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

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}

