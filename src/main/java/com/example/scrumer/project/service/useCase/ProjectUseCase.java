package com.example.scrumer.project.service.useCase;

import com.example.scrumer.project.command.*;
import com.example.scrumer.project.entity.Project;
import com.example.scrumer.task.command.CreateTaskCommand;
import javassist.NotFoundException;

import java.util.List;

public interface ProjectUseCase {
    Project findById(Long id) throws IllegalAccessException, NotFoundException;

    Project addProject(CreateProjectCommand command, String email);

    void removeById(Long id);

    List<Project> findAll();

    void updateProject(UpdateProjectCommand command) throws NotFoundException, IllegalAccessException;

    List<Project> findByUser(String userEmail);

    void addTask(Long id, CreateTaskCommand command) throws NotFoundException, IllegalAccessException;

    void addTeam(Long id, AddTeamCommand command) throws NotFoundException, IllegalAccessException;

    void removeTeam(Long id, Long idTeam) throws NotFoundException, IllegalAccessException;

    void updateProjectCover(UpdateProjectCoverCommand command);

    void removeProjectCover(Long id);

    List<SuggestedProject> findByName(String name);
}
