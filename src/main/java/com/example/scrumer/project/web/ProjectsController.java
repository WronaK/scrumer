package com.example.scrumer.project.web;

import com.example.scrumer.project.application.port.ProjectsUseCase;
import com.example.scrumer.project.application.port.ProjectsUseCase.CreateProjectCommand;
import com.example.scrumer.project.application.port.ProjectsUseCase.TeamCommand;
import com.example.scrumer.project.application.port.ProjectsUseCase.UpdateProjectCommand;
import com.example.scrumer.project.converter.ProjectToRestCommandConverter;
import com.example.scrumer.project.domain.Project;

import com.example.scrumer.project.request.ProjectRequest;
import com.example.scrumer.project.request.UpdateProjectRequest;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.converter.TaskToRestCommandConverter;
import com.example.scrumer.task.request.TaskRequest;
import com.example.scrumer.team.converter.TeamToRestCommandConverter;
import com.example.scrumer.team.request.TeamRequest;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@AllArgsConstructor
public class ProjectsController {
    private final ProjectsUseCase projects;
    private final ProjectToRestCommandConverter projectConverter;
    private final TaskToRestCommandConverter taskConverter;
    private final TeamToRestCommandConverter teamConverter;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public List<ProjectRequest> getAll() {
        return projects.findAll().stream()
                .map(projectConverter::toDto)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/my-projects")
    public List<ProjectRequest> getAllByLoggerUser() {
        return projects.findByUser(this.getUserEmail()).stream()
                .map(projectConverter::toDto)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/teams")
    public ResponseEntity<List<TeamRequest>> getTeams(@PathVariable Long id) throws IllegalAccessException, NotFoundException {
        return projects.findById(id)
                .map(project ->
                        ResponseEntity.ok(project.getTeams()
                                .stream()
                                .map(teamConverter::toDto)
                                .collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<ProjectRequest> getById(@PathVariable Long id) throws IllegalAccessException, NotFoundException {
        return projects.findById(id)
                .map(project -> ResponseEntity.ok(projectConverter.toDto(project)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/update")
    public ResponseEntity<UpdateProjectRequest> getProjectById(@PathVariable Long id) throws IllegalAccessException, NotFoundException {
        return projects.findById(id)
                .map(project -> ResponseEntity.ok(projectConverter.toDtoUpdate(project)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/product_backlog")
    public ResponseEntity<List<TaskRequest>> getProductBacklogById(@PathVariable Long id) throws IllegalAccessException, NotFoundException {
        return projects.findById(id)
                .map(project -> ResponseEntity.ok(project.getProductBacklog()
                    .stream()
                    .map(taskConverter::toDto)
                    .collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }

    @Secured({"ROLE_USER"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addProject(@RequestBody RestCreateProjectCommand command) {
        Project project = projects.addProject(command.toCreateCommand(), getUserEmail());
        return ResponseEntity.created(createdProjectUri(project)).build();
    }

    @Secured({"ROLE_USER"})
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateProject(@RequestBody UpdateProjectCommand command) throws NotFoundException, IllegalAccessException {
        projects.updateProject(command);
    }

    @Secured({"ROLE_USER"})
    @PutMapping("/{id}/product_backlog")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addTaskToProductBacklog(@PathVariable Long id,
                                        @RequestBody RestCreateTaskCommand command) throws NotFoundException, IllegalAccessException {
        projects.addTask(id, command.toCreateCommand());
    }

    @Secured({"ROLE_USER"})
    @PutMapping("/{id}/teams")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addTeams(@PathVariable Long id, @RequestBody RestTeamsCommand command) throws NotFoundException, IllegalAccessException {
        projects.addTeams(id, command.toCommands());
    }

    @Secured({"ROLE_USER"})
    @PatchMapping("/{id}/teams/{idTeam}/remove")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeTeam(@PathVariable Long id, @PathVariable Long idTeam) throws NotFoundException, IllegalAccessException {
        projects.removeTeam(id, idTeam);
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
        private String productOwner;
        private String scrumMaster;
        private Set<RestTeamCommand> teams;

        CreateProjectCommand toCreateCommand() {
            return new CreateProjectCommand(name, accessCode, description, productOwner, scrumMaster, toCommands(teams));
        }

        Set<TeamCommand> toCommands(Set<RestTeamCommand> teams) {
            Set<TeamCommand> listTeam = new HashSet<>();
            for (RestTeamCommand team: teams) {
                listTeam.add(team.toCommand());
            }
            return listTeam;
        }
    }

    @Data
    private static class RestCreateTaskCommand {
        private String title;
        private String description;
        private String priority;

        CreateTaskCommand toCreateCommand() {
            return new CreateTaskCommand(title, description, priority);
        }
    }

    @Data
    private static class RestTeamsCommand {
        private Set<RestTeamCommand> teams;

        Set<TeamCommand> toCommands() {
            Set<TeamCommand> listTeam = new HashSet<>();
            for (RestTeamCommand team: this.teams) {
                listTeam.add(team.toCommand());
            }
            return listTeam;
        }
    }

    @Data
    private static class RestTeamCommand {
        private String name;
        private String accessCode;

        TeamCommand toCommand() {
            return new TeamCommand(name, accessCode);
        }

    }
}
