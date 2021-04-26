package com.example.scrumer.project.web;

import com.example.scrumer.project.application.ProjectsService;
import com.example.scrumer.project.application.port.ProjectsUseCase.CreateProjectCommand;
import com.example.scrumer.project.domain.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectsController {
    private final ProjectsService projects;

    @GetMapping
    public List<Project> getAll() {
        return projects.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Project> getById(@PathVariable Long id) {
        return projects.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProject(@RequestBody RestCreateProjectCommand command) {
        projects.addProject(command.toCreateCommand());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        projects.removeById(id);
    }

    @Data
    private static class RestCreateProjectCommand {
        private String name;
        private String accessCode;

        CreateProjectCommand toCreateCommand() {
            return new CreateProjectCommand(name, accessCode);
        }
    }
}