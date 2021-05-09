package com.example.scrumer.team.application.port;

import com.example.scrumer.team.domain.Team;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface TeamsUseCase {
    List<Team> findAll();

    Optional<Team> findById(Long id);

    Team addTeam(CreateTeamCommand command);

    void deleteById(Long id);

    void addMember(Long id, MemberCommand command);

    void addProjectToTeam(Long id, ProjectCommand command);

    @Value
    class CreateTeamCommand {
        String name;
        String accessCode;

        public Team toTeam() {
            return new Team(name, accessCode);
        }
    }
    
    @Value
    class MemberCommand {
        String email;
    }

    @Value
    class ProjectCommand {
        String name;
        String accessCode;
    }
}
