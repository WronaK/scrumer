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
import java.util.Set;
import java.util.stream.Collectors;

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
    public void addMember(Long id, MemberCommand command) {
        repository.findById(id).ifPresent(team -> {
            userRepository.findByEmail(command.getEmail()).ifPresent(
                    team::addMember
            );
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
                    repository.save(team);
                });
    }

    @Override
    public List<Team> findByUser(String userEmail) {
        return repository.findByUser(userEmail);
    }

    @Override
    public List<Team> findByProjectId(Long id) {
        return repository.findByProjectId(id);
    }

    private Set<User> fetchUserByEmail(Set<MemberCommand> members) {
        return members.stream()
                .map(member -> userRepository.findByEmail(member.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("Not found user by email: " + member.getEmail()))
                ).collect(Collectors.toSet());
    }
}
