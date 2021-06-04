package com.example.scrumer.team.application;

import com.example.scrumer.team.application.port.TeamMembersUseCase;
import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.db.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamsService implements TeamsUseCase {
    private final TeamJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final TeamMembersService teamMembers;

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

        team.addMembers(this.teamMembers.fetchUserByEmail(command.getMembers()));
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
    public void updateTeam(UpdateTeamCommand toCommand) {
        repository.findById(toCommand.getId())
                .map(team -> {
                    this.updateFields(toCommand, team);
                    return repository.save(team);
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
}
