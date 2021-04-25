package com.example.scrumer.team.application.port;

import com.example.scrumer.team.domain.Team;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface TeamsUseCase {
    List<Team> findAll();

    Optional<Team> findById(Long id);

    Team addTeam(CreateTeamCommand toCommand);

    void deleteById(Long id);

    @Value
    class CreateTeamCommand {
        String name;
        String accessCode;

        public Team toTeam() {
            return new Team(name, accessCode);
        }
    }
}
