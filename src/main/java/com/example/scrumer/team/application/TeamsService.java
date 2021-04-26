package com.example.scrumer.team.application;

import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.db.UserJpaRepository;
import com.example.scrumer.user.domain.User;
import com.example.scrumer.user.web.UserController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamsService implements TeamsUseCase {
    private final TeamJpaRepository repository;
    private final UserJpaRepository userRepository;

    @Override
    public List<Team> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Team addTeam(CreateTeamCommand command) {
        Team team = command.toTeam();
        return repository.save(team);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void addMember(Long id, MemberCommand command) {
        repository.findById(id)
                .ifPresent(team -> {
                    User user = userRepository.findByEmail(command.getEmail());
                    user.addTeam(team);
                    userRepository.save(user);
                    team.addMember(user);
                    repository.save(team);
                });
    }
}
