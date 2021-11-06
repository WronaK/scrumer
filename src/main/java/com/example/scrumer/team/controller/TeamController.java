package com.example.scrumer.team.controller;

import com.example.scrumer.project.command.ProjectCommand;
import com.example.scrumer.project.mapper.ProjectMapper;
import com.example.scrumer.team.command.*;
import com.example.scrumer.team.entity.Team;
import com.example.scrumer.team.mapper.TeamMapper;
import com.example.scrumer.team.service.useCase.TeamUseCase;
import com.example.scrumer.user.command.UserCommand;
import com.example.scrumer.user.mapper.UserMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor public class TeamController {
    private final TeamUseCase teams;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public List<TeamCommand> getAll() {
        return teams.findByUser(this.getUserEmail())
                .stream()
                .map(TeamMapper::toCommand)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/my-teams")
    public List<TeamCommand> getAllByLoggerUser() {
        return teams.findByUser(this.getUserEmail())
                .stream()
                .map(TeamMapper::toCommand)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/projects")
    public List<ProjectCommand> getProjects(@PathVariable Long id) throws NotFoundException, IllegalAccessException {
        return teams.findById(id)
                .getProjects()
                .stream()
                .map(ProjectMapper::toProjectCommand)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/members")
    public List<UserCommand> getMembers(@PathVariable Long id) throws NotFoundException, IllegalAccessException {
        return teams.findById(id)
                .getMembers()
                .stream()
                .map(UserMapper::toUserCommand)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public TeamDetailsCommand getTeamById(@PathVariable Long id) throws NotFoundException, IllegalAccessException {
        return TeamMapper.toTeamDetailsCommand(teams.findById(id));
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/sprint_backlog")
    public SprintBacklogCommand getSprintBacklogById(@PathVariable Long id) throws NotFoundException, IllegalAccessException {
        return new SprintBacklogCommand(teams.findById(id).getSprintBoard());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addTeam(@RequestBody CreateTeamCommand command) {
        Team team = teams.addTeam(command, getUserEmail());
        return ResponseEntity.created(createdTeamUri(team)).build();
    }

    @Secured({"ROLE_USER"})
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTeam(@RequestBody UpdateTeamCommand command) throws NotFoundException, IllegalAccessException {
        teams.updateTeam(command);
    }

    @Secured({"ROLE_USER"})
    @PutMapping("/{id}/members")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addMemberToTeam(@PathVariable Long id, @RequestBody MemberTeamCommand command) throws NotFoundException, IllegalAccessException {
        teams.addMember(id, command);
    }

    @Secured({"ROLE_USER"})
    @PatchMapping("/{id}/task/{idTask}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addTaskToSprintBacklog(@PathVariable Long id, @PathVariable Long idTask) throws NotFoundException, IllegalAccessException {
        teams.addTask(id, idTask);
    }

    @Secured({"ROLE_USER"})
    @PatchMapping("/{id}/projects/{idProject}/remove")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeProject(@PathVariable Long id, @PathVariable Long idProject) throws NotFoundException, IllegalAccessException {
        teams.removeProject(id, idProject);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        teams.deleteById(id);
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
    private URI createdTeamUri(Team team) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + team.getId().toString()).build().toUri();
    }
}
