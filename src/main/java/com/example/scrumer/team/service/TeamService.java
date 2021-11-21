package com.example.scrumer.team.service;

import com.example.scrumer.project.repository.ProjectJpaRepository;
import com.example.scrumer.security.ValidatorPermission;
import com.example.scrumer.task.entity.StatusTask;
import com.example.scrumer.task.repository.TaskJpaRepository;
import com.example.scrumer.team.command.CreateTeamCommand;
import com.example.scrumer.team.command.UpdateTeamCommand;
import com.example.scrumer.team.command.UpdateTeamCoverCommand;
import com.example.scrumer.team.entity.Team;
import com.example.scrumer.team.repository.TeamJpaRepository;
import com.example.scrumer.team.service.useCase.TeamUseCase;
import com.example.scrumer.upload.command.SaveUploadCommand;
import com.example.scrumer.upload.entity.UploadEntity;
import com.example.scrumer.upload.service.useCase.UploadUseCase;
import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.repository.UserJpaRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService implements TeamUseCase {
    private final TeamJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final ValidatorPermission validatorPermission;
    private final TaskJpaRepository tasksRepository;
    private final ProjectJpaRepository projectRepository;
    private final UploadUseCase uploadUseCase;

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
    public Team addTeam(CreateTeamCommand command) {
        Team team = Team.builder()
                .teamName(command.getTeamName())
                .description(command.getDescription())
                .accessCode(command.getAccessCode())
                .build();

        userRepository.findById(command.getScrumMaster()).ifPresent(team::setScrumMaster);
        return repository.save(team);
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

        User user = userRepository.findById(idMember).orElseThrow(() -> new NotFoundException("Not found user with id:" + idMember));
        team.addMember(user);
        repository.save(team);
    }

    @Override
    public void addTask(Long id, Long idTask) throws NotFoundException, IllegalAccessException {
        Team team = findById(id);

        tasksRepository.findById(idTask).ifPresent(task -> {
            task.setStatusTask(StatusTask.FOR_IMPLEMENTATION);
            team.addTaskToSprintBacklog(task);
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

    private void updateFields(UpdateTeamCommand toCommand, Team team) {
        if(toCommand.getName() != null) {
            team.setTeamName(toCommand.getName());
        }

        if(toCommand.getAccessCode() != null) {
            team.setAccessCode(toCommand.getAccessCode());
        }
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
