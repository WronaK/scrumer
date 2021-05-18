package com.example.scrumer.team.application;

import com.example.scrumer.project.db.ProjectJpaRepository;
import com.example.scrumer.project.domain.Project;
import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamsService implements TeamsUseCase {
    private final TeamJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final ProjectJpaRepository projectRepository;

    @Override
    public List<Team> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Team addTeam(CreateTeamCommand command, String email) {
        Team team = new Team(command.getName(), command.getAccessCode());

        userRepository.findByEmail(email).ifPresent(creator -> {
            team.addCreator(creator);
            team.addMember(creator);
            Team save = repository.save(team);
            creator.addTeam(save);
            userRepository.save(creator);
        });
        Team savedTeam = repository.save(team);
        this.addMembers(command.getMembers(), savedTeam);
        return repository.save(savedTeam);
    }

    private void addMembers(List<MemberCommand> commands, Team team) {
        for(MemberCommand command: commands) {
            this.addMember(team.getId(), command);
        }
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void addMember(Long id, MemberCommand command) {
        repository.findById(id)
                .ifPresent(team -> {
                    User user = userRepository.findByEmail(command.getEmail()).get();
                    user.addTeam(team);
                    userRepository.save(user);
                    team.addMember(user);
                    repository.save(team);
                });
    }

    @Override
    public void addProjectToTeam(Long id, ProjectCommand command) {
        repository.findById(id)
                .ifPresent(team -> {
                    Project project = projectRepository
                            .findByNameAndAccessCode(command.getName(), command.getAccessCode());
                    project.addTeam(team);
                    projectRepository.save(project);
                    team.addProject(project);
                    repository.save(team);
                });
    }

    @Override
    public List<Team> findByUser(String userEmail) {
        return repository.findByUser(userEmail);
    }
}
