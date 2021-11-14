package com.example.scrumer.team.service;

import com.example.scrumer.project.repository.ProjectJpaRepository;
import com.example.scrumer.security.ValidatorPermission;
import com.example.scrumer.task.repository.TaskJpaRepository;
import com.example.scrumer.task.entity.StatusTask;
import com.example.scrumer.team.command.CreateTeamCommand;
import com.example.scrumer.team.command.MemberTeamCommand;
import com.example.scrumer.team.command.UpdateTeamCommand;
import com.example.scrumer.team.entity.Team;
import com.example.scrumer.team.repository.TeamJpaRepository;
import com.example.scrumer.team.service.useCase.TeamUseCase;
import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.repository.UserJpaRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService implements TeamUseCase {
    private final TeamJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final ValidatorPermission validatorPermission;
    private final TaskJpaRepository tasksRepository;
    private final ProjectJpaRepository projectRepository;

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
    public Optional<Team> findByIdTeam(Long id) {
        return repository.findById(id);
    }

    @Override
    public Team addTeam(CreateTeamCommand command, String email) {
        Team team = new Team(command.getName(), command.getAccessCode());

        userRepository.findByEmail(email).ifPresent(creator -> {
            team.setCreator(creator);
            team.addMember(creator);
        });

        team.addMembers(this.fetchUserByEmail(command.getMembers()));
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
    public void addMember(Long id, MemberTeamCommand command) throws NotFoundException, IllegalAccessException {
        Team team = findById(id);

        User user = userRepository.findByEmail(command.getEmail()).orElseThrow(() -> new NotFoundException("Not found user with email:" + command.getEmail()));
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

    private void updateFields(UpdateTeamCommand toCommand, Team team) {
        if(toCommand.getName() != null) {
            team.setName(toCommand.getName());
        }

        if(toCommand.getAccessCode() != null) {
            team.setAccessCode(toCommand.getAccessCode());
        }
    }

    private Set<User> fetchUserByEmail(Set<MemberTeamCommand> members) {
        return members.stream()
                .map(member -> userRepository.findByEmail(member.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("Not found user by email: " + member.getEmail()))
                ).collect(Collectors.toSet());
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
