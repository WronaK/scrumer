package com.example.scrumer.team.service.useCase;

import com.example.scrumer.project.command.AddTeamCommand;
import com.example.scrumer.team.command.*;
import com.example.scrumer.team.entity.Team;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TeamUseCase {
    List<Team> findAll();

    Team findById(Long id) throws NotFoundException, IllegalAccessException;

    Optional<Team> findTeamById(Long id);

    void createTeam(CreateTeamCommand command);

    void deleteById(Long id);

    List<Team> findByUser(String userEmail);

    void updateTeam(UpdateTeamCommand toCommand) throws NotFoundException, IllegalAccessException;

    void addMember(Long idTeam, Long idMember) throws NotFoundException, IllegalAccessException;

    void moveUserStoryToTeam(Long id, Long idUserStory) throws NotFoundException, IllegalAccessException;

    void removeProject(Long id, Long idProject) throws NotFoundException, IllegalAccessException;

    void updateTeamCover(UpdateTeamCoverCommand updateTeamCoverCommand);

    List<SuggestedTeam> findByName(String name);

    void addProject(Long id, AddProjectCommand command) throws NotFoundException, IllegalAccessException;

    void joinToTeam(String userEmail, AddTeamCommand command);

    void addAttachment(Long id, MultipartFile file);
}
