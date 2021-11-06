package com.example.scrumer.team.service.useCase;

import com.example.scrumer.team.command.CreateTeamCommand;
import com.example.scrumer.team.command.MemberTeamCommand;
import com.example.scrumer.team.command.UpdateTeamCommand;
import com.example.scrumer.team.entity.Team;
import javassist.NotFoundException;
import lombok.Value;

import java.util.List;
import java.util.Set;

public interface TeamUseCase {
    List<Team> findAll();

    Team findById(Long id) throws NotFoundException, IllegalAccessException;

    Team addTeam(CreateTeamCommand command, String email);

    void deleteById(Long id);

    List<Team> findByUser(String userEmail);

    void updateTeam(UpdateTeamCommand toCommand) throws NotFoundException, IllegalAccessException;

    void addMember(Long id, MemberTeamCommand command) throws NotFoundException, IllegalAccessException;

    void addTask(Long id, Long idTask) throws NotFoundException, IllegalAccessException;

    void removeProject(Long id, Long idProject) throws NotFoundException, IllegalAccessException;
}
