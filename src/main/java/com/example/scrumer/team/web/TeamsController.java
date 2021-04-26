package com.example.scrumer.team.web;

import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.application.port.TeamsUseCase.CreateTeamCommand;
import com.example.scrumer.team.application.port.TeamsUseCase.MemberCommand;
import com.example.scrumer.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamsController {
    private final TeamsUseCase teams;

    @GetMapping
    public List<Team> getAll() {
        return teams.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Team> getTeamById(@PathVariable Long id) {
        return teams.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTeam(@RequestBody RestCreateTeamCommand command) {
        Team team = teams.addTeam(command.toCommand());
    }

    @PutMapping("/{id}/members")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addMemberToTeam(@PathVariable Long id, @RequestBody RestMemberCommand command) {
        teams.addMember(id, command.toCommand());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        teams.deleteById(id);
    }

    @Data
    private static class RestCreateTeamCommand {
        String name;
        String accessCode;

        CreateTeamCommand toCommand() {
            return new CreateTeamCommand(name, accessCode);
        }
    }

    @Data
    private static class RestMemberCommand {
        String email;

        MemberCommand toCommand() {
            return new MemberCommand(email);
        }
    }
}
