package com.example.scrumer.team.application;

import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.db.TeamJpaRepository;
import com.example.scrumer.team.domain.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamsService implements TeamsUseCase {
    private final TeamJpaRepository repository;

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
}
