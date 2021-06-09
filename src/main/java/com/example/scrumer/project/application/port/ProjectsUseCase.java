package com.example.scrumer.project.application.port;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.application.port.TasksUseCase;
import javassist.NotFoundException;
import lombok.Value;

import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectsUseCase {
    Optional<Project> findById(Long id) throws IllegalAccessException, NotFoundException;

    Project addProject(CreateProjectCommand command, String email);

    void removeById(Long id);

    List<Project> findAll();

    void updateProject(UpdateProjectCommand command) throws NotFoundException, IllegalAccessException;

    List<Project> findByUser(String userEmail);

    void addTask(Long id, TasksUseCase.CreateTaskCommand command) throws NotFoundException, IllegalAccessException;

    void addTeams(Long id, Set<TeamCommand> command) throws NotFoundException, IllegalAccessException;

    void removeTeam(Long id, Long idTeam) throws NotFoundException, IllegalAccessException;

    @Value
    class CreateProjectCommand {
        String name;
        String accessCode;
        String description;
        String productOwner;
        String scrumMaster;
        Set<TeamCommand> teams;
    }

    @Value
    class TeamCommand {
        String name;
        String accessCode;
    }

    @Value
    class UpdateProjectCommand {
        Long id;
        String name;
        String accessCode;
        String description;
        String productOwner;
        String scrumMaster;
    }
}
