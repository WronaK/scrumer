package com.example.scrumer.project.controller;

import com.example.scrumer.project.command.*;
import com.example.scrumer.project.entity.Project;
import com.example.scrumer.project.mapper.ProjectMapper;
import com.example.scrumer.project.service.useCase.ProjectUseCase;
import com.example.scrumer.task.command.CreateTaskCommand;
import com.example.scrumer.task.mapper.TaskMapper;
import com.example.scrumer.task.command.TaskCommand;
import com.example.scrumer.team.command.TeamCommand;
import com.example.scrumer.team.mapper.TeamMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectUseCase projects;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public List<ProjectDetailsCommand> getAll() {
        return projects.findAll().stream()
                .map(ProjectMapper::toProjectDetailsCommand)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/my-projects")
    public List<ProjectDetailsCommand> getAllByLoggerUser() {
        return projects.findByUser(this.getUserEmail()).stream()
                .map(ProjectMapper::toProjectDetailsCommand)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/information")
    public ProjectInformationCommand getInformationById(@PathVariable Long id) throws IllegalAccessException, NotFoundException {
        return ProjectMapper.toProjectInformationCommand(projects.findById(id));
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/teams")
    public List<TeamCommand> getTeams(@PathVariable Long id) throws IllegalAccessException, NotFoundException {
        return projects.findById(id)
                .getTeams()
                .stream()
                .map(TeamMapper::toCommand)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ProjectDetailsCommand getById(@PathVariable Long id) throws IllegalAccessException, NotFoundException {
        return ProjectMapper.toProjectDetailsCommand(projects.findById(id));
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/update")
    public UpdateProjectCommand getProjectById(@PathVariable Long id) throws IllegalAccessException, NotFoundException {
        return ProjectMapper.toUpdateProjectCommand(projects.findById(id));
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/product_backlog")
    public List<TaskCommand> getProductBacklogById(@PathVariable Long id) throws IllegalAccessException, NotFoundException {
        return projects.findById(id)
                .getProductBacklog()
                .stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProject(@RequestBody CreateProjectCommand command) {
        Project project = projects.addProject(command, getUserEmail());
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
                                        @RequestBody CreateTaskCommand command) throws NotFoundException, IllegalAccessException {
        projects.addTask(id, command);
    }

    @Secured({"ROLE_USER"})
    @PutMapping("/{id}/teams")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addTeam(@PathVariable Long id, @RequestBody AddTeamCommand command) throws NotFoundException, IllegalAccessException {
        projects.addTeam(id, command);
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

    @PostMapping("/{id}/cover")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addCoverProject(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("Got file: " + file.getOriginalFilename());
        projects.updateProjectCover(new UpdateProjectCoverCommand(
                id,
                file.getBytes(),
                file.getContentType(),
                file.getOriginalFilename()
        ));
    }

    @DeleteMapping("/{id}/cover")
    public void removeBookCover(@PathVariable Long id) {
        projects.removeProjectCover(id);
    }
}
