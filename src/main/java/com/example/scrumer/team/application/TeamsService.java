package com.example.scrumer.team.application;

import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.security.ValidatorPermission;
import com.example.scrumer.task.db.TaskJpaRepository;
import com.example.scrumer.task.domain.StatusTask;
import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.user.domain.User;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamsService implements TeamsUseCase {
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
    public Optional<Team> findById(Long id) throws NotFoundException, IllegalAccessException {
        Optional<Team> team = repository.findById(id);
        this.validatorPermission.validateTeamPermission(team, this.getUserEmail());
        return team;
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
        Optional<Team> team = repository.findById(toCommand.getId());
        validatorPermission.validateTeamPermission(team, this.getUserEmail());

        Team savedTeam = team.get();
        this.updateFields(toCommand, savedTeam);
        repository.save(savedTeam);
    }

    @Override
    public void addMember(Long id, Set<MemberCommand> command) throws NotFoundException, IllegalAccessException {
        Optional<Team> team = repository.findById(id);
        this.validatorPermission.validateTeamPermission(team, this.getUserEmail());

        Team savedTeam = team.get();
        savedTeam.addMembers(this.fetchUserByEmail(command));
        repository.save(savedTeam);
    }

    @Override
    public void addTask(Long id, Long idTask) throws NotFoundException, IllegalAccessException {
        Optional<Team> team = repository.findById(id);
        this.validatorPermission.validateTeamPermission(team, this.getUserEmail());

        Team savedTeam = team.get();
        tasksRepository.findById(idTask).ifPresent(task -> {
            task.setStatusTask(StatusTask.FOR_IMPLEMENTATION);
            savedTeam.addTaskToSprintBacklog(task);
            repository.save(savedTeam);
        });
    }

    @Override
    public void removeProject(Long id, Long idProject) throws NotFoundException, IllegalAccessException {
        Optional<Team> team = repository.findById(id);
        this.validatorPermission.validateModifyTeamPermission(team, this.getUserEmail());

        Team savedTeam = team.get();
        this.projectRepository.findById(idProject).ifPresent(project -> {
            savedTeam.removeProject(project);
            repository.save(savedTeam);
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

    private Set<User> fetchUserByEmail(Set<TeamsUseCase.MemberCommand> members) {
        return members.stream()
                .map(member -> userRepository.findByEmail(member.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("Not found user by email: " + member.getEmail()))
                ).collect(Collectors.toSet());
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
