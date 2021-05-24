package com.example.scrumer.team.web;

import com.example.scrumer.project.application.port.ProjectsUseCase;
import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.application.port.TeamsUseCase.CreateTeamCommand;
import com.example.scrumer.team.application.port.TeamsUseCase.MemberCommand;
import com.example.scrumer.team.application.port.TeamsUseCase.ProjectCommand;
import com.example.scrumer.team.converter.TeamToTeamRequestConverter;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.team.request.TeamRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamsController {
    private final TeamsUseCase teams;
    private final TeamToTeamRequestConverter teamConverter;

    @GetMapping
    public List<Team> getAll() {
        return teams.findByUser(this.getUserEmail());
    }

    @GetMapping("/{id}/project")
    public List<TeamRequest> getTeamByProjectId(@PathVariable Long id) {
        return teams.findByProjectId(id).stream()
                .map(teamConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<Team> getTeamById(@PathVariable Long id) {
        return teams.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addTeam(@RequestBody RestCreateTeamCommand command) {
        Team team = teams.addTeam(command.toCommand(), getUserEmail());
        return ResponseEntity.created(createdTeamUri(team)).build();
    }

    @PutMapping("/{id}/members")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addMemberToTeam(@PathVariable Long id, @RequestBody RestMemberCommand command) {
        teams.addMember(id, command.toCommand());
    }

    @PutMapping("/{id}/projects")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addProjectToTeam(@PathVariable Long id, @RequestBody RestProjectCommand command) {
        teams.addProjectToTeam(id, command.toCommand());
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
    @Data
    private static class RestCreateTeamCommand {
        String name;
        String accessCode;
        Set<RestMemberCommand> members;

        CreateTeamCommand toCommand() {
            return new CreateTeamCommand(name, accessCode, toCommands(members));
        }

        Set<MemberCommand> toCommands(Set<RestMemberCommand> members) {
            Set<MemberCommand> listMembers = new HashSet<>();
            for(RestMemberCommand member: members) {
                listMembers.add(member.toCommand());
            }
            return listMembers;
        }
    }

    @Data
    private static class RestMemberCommand {
        String email;

        MemberCommand toCommand() {
            return new MemberCommand(email);
        }
    }

    @Data
    private static class RestProjectCommand {
        String name;
        String accessCode;

        ProjectCommand toCommand() {
            return new ProjectCommand(name, accessCode);
        }
    }
}
