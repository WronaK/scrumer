package com.example.scrumer.team.application.port;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.domain.User;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeamsUseCase {
    List<Team> findAll();

    Optional<Team> findById(Long id);

    Team addTeam(CreateTeamCommand command, String email);

    void deleteById(Long id);

    List<Team> findByUser(String userEmail);

    void updateTeam(UpdateTeamCommand toCommand);

    @Value
    class CreateTeamCommand {
        String name;
        String accessCode;
        Set<MemberCommand> members;
    }

    @Value
    class UpdateTeamCommand {
        Long id;
        String name;
        String accessCode;
    }
    
    @Value
    class MemberCommand {
        String email;
    }
}
