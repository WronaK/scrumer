package com.example.scrumer.project.web;

import com.example.scrumer.project.application.ProjectsService;
import com.example.scrumer.project.application.port.ProjectsUseCase.CreateProjectCommand;
import com.example.scrumer.project.domain.Project;

import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
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

    @GetMapping("/{id}/product_backlog")
    public List<Task> getProductBacklogById(@PathVariable Long id) {
        return projects.getProductBacklog(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addProject(@RequestBody RestCreateProjectCommand command) {
        System.out.println("Project");
        Project project = projects.addProject(command.toCreateCommand(), getUserEmail());
        return ResponseEntity.created(createdProjectUri(project)).build();
    }

    @PutMapping("/{id}/product_backlog")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addTaskToProductBacklog(@PathVariable Long id,
                                        @RequestBody RestCreateTaskCommand command) {
        System.out.println("Add " + id);
        projects.addTaskToProductBacklog(id, command.toCreateCommand());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        projects.removeById(id);
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
    private URI createdProjectUri(Project project) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + project.getId().toString()).build().toUri();
    }

    @Data
    private static class RestCreateProjectCommand {
        private String name;
        private String accessCode;
        private String description;

        CreateProjectCommand toCreateCommand() {
            return new CreateProjectCommand(name, accessCode, description);
        }
    }

    @Data
    private static class RestCreateTaskCommand {
        private String title;
        private String description;
        private Integer priority;

        CreateTaskCommand toCreateCommand() {
            return new CreateTaskCommand(title, description, priority);
        }
    }
}
