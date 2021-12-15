package com.example.scrumer.project.service;

import com.example.scrumer.issue.command.CreateUserStoryCommand;
import com.example.scrumer.issue.command.ExportUserStoryCommand;
import com.example.scrumer.issue.entity.StatusIssue;
import com.example.scrumer.issue.entity.UserStory;
import com.example.scrumer.issue.mapper.UserStoryMapper;
import com.example.scrumer.project.command.*;
import com.example.scrumer.project.entity.Project;
import com.example.scrumer.project.repository.ProjectJpaRepository;
import com.example.scrumer.project.service.useCase.ProjectUseCase;
import com.example.scrumer.security.ValidatorPermission;
import com.example.scrumer.issue.command.CreateIssueCommand;
import com.example.scrumer.team.repository.TeamJpaRepository;
import com.example.scrumer.upload.command.SaveUploadCommand;
import com.example.scrumer.upload.entity.UploadEntity;
import com.example.scrumer.upload.service.useCase.UploadUseCase;
import com.example.scrumer.user.repository.UserJpaRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService implements ProjectUseCase {
    private final ProjectJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final ValidatorPermission validatorPermission;
    private final TeamJpaRepository teamsRepository;
    private final UploadUseCase uploadUseCase;

    @Override
    public Project findById(Long id) throws IllegalAccessException, NotFoundException {
        Project project = repository.findById(id).orElseThrow(() -> new NotFoundException("Not found project with id: " + id));
        this.validatorPermission.validateProjectPermission(project, this.getUserEmail());
        return project;
    }

    @Override
    public Project addProject(CreateProjectCommand command, String email) {
        Project project = Project.builder()
                .projectName(command.getProjectName())
                .description(command.getDescription())
                .accessCode(command.getAccessCode())
                .build();
        userRepository.findById(command.getProductOwner()).ifPresent(project::setProductOwner);
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
        Project project = findById(command.getId());

        this.updateFields(command, project);
        repository.save(project);
    }

    @Override
    public List<Project> findByUser(String userEmail) {
        return repository.findProjectByUser(userEmail);
    }

    @Override
    public void addUserStory(Long id, CreateUserStoryCommand command) throws NotFoundException, IllegalAccessException {
        Project project = findById(id);

        UserStory userStory = UserStory.builder()
                .title(command.getTitle())
                .description(command.getDescription())
                .priority(command.getPriority())
                .statusIssue(StatusIssue.NEW)
                .build();

        project.addUserStory(userStory);
        repository.save(project);
    }

    @Override
    public void addTeam(Long id, AddTeamCommand command) throws NotFoundException, IllegalAccessException {
        Project project = findById(id);
        this.validatorPermission.validateModifyProjectPermission(project, this.getUserEmail());

        this.addTeam(project, command);
        repository.save(project);
    }

    @Override
    public void removeTeam(Long id, Long idTeam) throws NotFoundException, IllegalAccessException {
        Project project = findById(id);

        this.teamsRepository.findById(idTeam).ifPresent(team -> {
            project.removeTeam(team);
            repository.save(project);
        });
    }

    @Override
    public void updateProjectCover(UpdateProjectCoverCommand command) {
        repository.findById(command.getId())
                .ifPresent(
                        project -> {
                            UploadEntity savedUpload = uploadUseCase.save(new SaveUploadCommand(command.getFilename(), command.getFile(), command.getContentType()));
                            project.setCoverId(savedUpload.getId());
                            repository.save(project);
                        }
                );
    }

    @Override
    public void removeProjectCover(Long id) {
        repository.findById(id)
                .ifPresent(project -> {
                    uploadUseCase.removeById(project.getCoverId());
                    project.setCoverId(null);
                    repository.save(project);
                });
    }

    @Override
    public List<SuggestedProject> findByName(String name) {
        return repository.findByStartedName(name).stream().map(project -> new SuggestedProject(project.getId(), project.getProjectName())).collect(Collectors.toList());
    }

    @Override
    public void addAttachment(Long id, MultipartFile file) {
        repository.findById(id).ifPresent(project -> {
            try {
                UploadEntity savedUpload = uploadUseCase.save(new SaveUploadCommand(file.getOriginalFilename(), file.getBytes(), file.getContentType()));
                project.addAttachment(savedUpload);
                repository.save(project);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public List<ExportUserStoryCommand> getProductBacklogByProjectId(Long idProject) {
        Optional<Project> project = repository.findById(idProject);

        return project.map(value -> value.getProductBacklog().stream().map(UserStoryMapper::toExportCommand).collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    private void addTeam(Project project, AddTeamCommand command) {
        teamsRepository.findTeamByIdAndAccessCode(command.getIdTeam(), command.getAccessCode())
                .ifPresent(project::addTeam);
    }

    private void updateFields(UpdateProjectCommand command, Project project) {
        if(command.getProjectName() != null) {
            project.setProjectName(command.getProjectName());
        }

        if(command.getDescription() != null) {
            project.setDescription(command.getDescription());
        }

        if(command.getAccessCode() != null) {
            project.setAccessCode(command.getAccessCode());
        }

        if(command.getProductOwner() != null) {
            userRepository.findById(command.getProductOwner())
                    .ifPresent(project::setProductOwner);
        }
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}

