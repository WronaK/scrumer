package com.example.scrumer.team.service;

import com.example.scrumer.issue.repository.UserStoryJpaRepository;
import com.example.scrumer.project.command.AddTeamCommand;
import com.example.scrumer.project.repository.ProjectJpaRepository;
import com.example.scrumer.security.ValidatorPermission;
import com.example.scrumer.issue.entity.StatusIssue;
import com.example.scrumer.issue.repository.IssueJpaRepository;
import com.example.scrumer.team.command.*;
import com.example.scrumer.team.entity.Team;
import com.example.scrumer.team.repository.TeamJpaRepository;
import com.example.scrumer.team.service.useCase.TeamUseCase;
import com.example.scrumer.upload.command.SaveUploadCommand;
import com.example.scrumer.upload.entity.UploadEntity;
import com.example.scrumer.upload.service.useCase.UploadUseCase;
import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.repository.UserJpaRepository;
import com.example.scrumer.user.service.UserService;
import com.example.scrumer.user.service.useCase.UserUseCase;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService implements TeamUseCase {
    private final TeamJpaRepository repository;
    private final UserUseCase userUseCase;
    private final ValidatorPermission validatorPermission;
    private final ProjectJpaRepository projectRepository;
    private final UploadUseCase uploadUseCase;
    private final UserStoryJpaRepository userStoryJpaRepository;

    @Override
    public List<Team> findAll() {
        return repository.findAll();
    }

    @Override
    public Team findById(Long id) throws NotFoundException, IllegalAccessException {
        Team team = repository.findById(id).orElseThrow(() -> new NotFoundException("Not found team wit id: " + id));
        this.validatorPermission.validateTeamPermission(team, this.getUserEmail());
        return team;
    }

    @Override
    public Optional<Team> findTeamById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void createTeam(CreateTeamCommand command) {
        Team team = Team.builder()
                .teamName(command.getTeamName())
                .description(command.getDescription())
                .accessCode(command.getAccessCode())
                .build();

        userUseCase.findUserById(command.getScrumMaster()).ifPresent(team::setScrumMaster);
        repository.save(team);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Team> findByUser(String userEmail) {
        return repository.findByUser(userEmail);
    }

    @Override
    public void updateTeam(UpdateTeamCommand toCommand) throws NotFoundException, IllegalAccessException {
        Team team = findById(toCommand.getId());

        this.updateFields(toCommand, team);
        repository.save(team);
    }

    @Override
    public void addMember(Long idTeam, Long idMember) throws NotFoundException, IllegalAccessException {
        Team team = findById(idTeam);

        User user = userUseCase.findById(idMember);
        team.addMember(user);
        repository.save(team);
    }

    @Override
    public void moveUserStoryToTeam(Long id, Long idUserStory) throws NotFoundException, IllegalAccessException {
        Team team = findById(id);

        userStoryJpaRepository.findById(idUserStory).ifPresent(task -> {
            task.setStatusIssue(StatusIssue.TO_BE_IMPLEMENTED);
            team.addUserStoryToSprintBacklog(task);
            repository.save(team);
        });
    }

    @Override
    public void removeProject(Long id, Long idProject) throws NotFoundException, IllegalAccessException {
        Team team = findById(id);
        this.projectRepository.findById(idProject).ifPresent(project -> {
            team.removeProject(project);
            repository.save(team);
        });
    }

    @Override
    public void updateTeamCover(UpdateTeamCoverCommand command) {
        repository.findById(command.getId())
                .ifPresent(
                        team -> {
                            UploadEntity savedUpload = uploadUseCase.save(new SaveUploadCommand(command.getFilename(), command.getFile(), command.getContentType()));
                            team.setCoverId(savedUpload.getId());
                            repository.save(team);
                        }
                );
    }

    @Override
    public List<SuggestedTeam> findByName(String name) {
        return repository.findByStartedName(name).stream().map(team -> new SuggestedTeam(team.getId(), team.getTeamName())).collect(Collectors.toList());
    }

    @Override
    public void addProject(Long id, AddProjectCommand command) throws NotFoundException, IllegalAccessException {
        Team team = findById(id);

        projectRepository.findProjectByIdAndAccessCode(command.getIdProject(), command.getAccessCode())
                .ifPresent(team::addProject);

        repository.save(team);
    }

    @Override
    public void joinToTeam(String userEmail, AddTeamCommand command) {
        Optional<User> user = userUseCase.findByEmail(userEmail);
        Optional<Team> team = repository.findTeamByIdAndAccessCode(command.getIdTeam(), command.getAccessCode());

        if (user.isPresent() && team.isPresent()) {
            team.get().addMember(user.get());
            repository.save(team.get());
        }
    }

    @Override
    public void addAttachment(Long id, MultipartFile file) {
        repository.findById(id).ifPresent(team -> {
            try {
                UploadEntity savedUpload = uploadUseCase.save(new SaveUploadCommand(file.getOriginalFilename(), file.getBytes(), file.getContentType()));
                team.addAttachment(savedUpload);
                repository.save(team);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateFields(UpdateTeamCommand toCommand, Team team) {
        if(toCommand.getTeamName() != null) {
            team.setTeamName(toCommand.getTeamName());
        }

        if (toCommand.getDescription() != null) {
            team.setDescription(toCommand.getDescription());
        }

        if(toCommand.getAccessCode() != null) {
            team.setAccessCode(toCommand.getAccessCode());
        }

        if(toCommand.getScrumMaster() != null) {
            userUseCase.findUserById(toCommand.getScrumMaster())
                    .ifPresent(team::setScrumMaster);
        }
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
