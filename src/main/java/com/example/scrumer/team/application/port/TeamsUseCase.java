package com.example.scrumer.team.application.port;

import com.example.scrumer.team.domain.Team;
import javassist.NotFoundException;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeamsUseCase {
    List<Team> findAll();

    Optional<Team> findById(Long id) throws NotFoundException, IllegalAccessException;

    Team addTeam(CreateTeamCommand command, String email);

    void deleteById(Long id);

    List<Team> findByUser(String userEmail);

    void updateTeam(UpdateTeamCommand toCommand) throws NotFoundException, IllegalAccessException;

    void addMember(Long id, Set<TeamsUseCase.MemberCommand> command) throws NotFoundException, IllegalAccessException;

    void addTask(Long id, Long idTask) throws NotFoundException, IllegalAccessException;

    void removeProject(Long id, Long idProject) throws NotFoundException, IllegalAccessException;

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
