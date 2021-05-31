package com.example.scrumer.team.application.port;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.domain.User;
import lombok.Value;

import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeamsUseCase {
    List<Team> findAll();

    Optional<Team> findById(Long id);

    Team addTeam(CreateTeamCommand command, String email);

    void deleteById(Long id);

    void addMember(Long id, Set<MemberCommand> command);

    void addProjectToTeam(Long id, ProjectCommand command);

    List<Team> findByUser(String userEmail);

    List<Team> findByProjectId(Long id);

    void addTask(Long id, Long idTask);

    List<Task> getSprintBacklog(Long id);

    List<User> findMembersById(Long id);

    List<Project> findProjectsById(Long id);

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

    @Value
    class ProjectCommand {
        String name;
        String accessCode;
    }
}
