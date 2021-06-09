package com.example.scrumer.team.web;

import com.example.scrumer.project.converter.ProjectToRestCommandConverter;
import com.example.scrumer.project.request.ProjectShortcutRequest;
import com.example.scrumer.task.converter.TaskToRestCommandConverter;
import com.example.scrumer.task.domain.Subtask;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.request.TaskRequest;
import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.application.port.TeamsUseCase.CreateTeamCommand;
import com.example.scrumer.team.application.port.TeamsUseCase.MemberCommand;
import com.example.scrumer.team.converter.TeamToRestCommandConverter;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.team.request.TeamDetails;
import com.example.scrumer.team.request.TeamRequest;
import com.example.scrumer.user.converter.UserToUserRequestConverter;
import com.example.scrumer.user.request.UserRequest;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamsController {
    private final TeamsUseCase teams;
    private final TeamToRestCommandConverter teamConverter;
    private final TaskToRestCommandConverter taskConverter;
    private final UserToUserRequestConverter userConverter;
    private final ProjectToRestCommandConverter projectConverter;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public List<TeamRequest> getAll() {
        return teams.findByUser(this.getUserEmail())
                .stream()
                .map(this.teamConverter::toDto)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/my-teams")
    public List<TeamRequest> getAllByLoggerUser() {
        return teams.findByUser(this.getUserEmail())
                .stream()
                .map(this.teamConverter::toDto)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/projects")
    public ResponseEntity<List<ProjectShortcutRequest>> getProjects(@PathVariable Long id) throws NotFoundException, IllegalAccessException {
        return teams.findById(id)
                .map(team ->
                        ResponseEntity.ok(team.getProjects()
                        .stream()
                        .map(projectConverter::toDtoShortcut)
                        .collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<UserRequest>> getMembers(@PathVariable Long id) throws NotFoundException, IllegalAccessException {
        return teams.findById(id)
                .map(team ->
                        ResponseEntity.ok(team.getMembers()
                        .stream()
                        .map(userConverter::toDto)
                        .collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<TeamDetails> getTeamById(@PathVariable Long id) throws NotFoundException, IllegalAccessException {
        return teams.findById(id)
                .map(team -> ResponseEntity.ok(teamConverter.toDtoDetails(team)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}/sprint_backlog")
    public ResponseEntity<RestSprintBacklog> getSprintBacklogById(@PathVariable Long id) throws NotFoundException, IllegalAccessException {
        return teams.findById(id)
                .map(team -> ResponseEntity.ok(new RestSprintBacklog(taskConverter, team.getSprintBoard())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addTeam(@RequestBody RestCreateTeamCommand command) {
        Team team = teams.addTeam(command.toCommand(), getUserEmail());
        return ResponseEntity.created(createdTeamUri(team)).build();
    }

    @Secured({"ROLE_USER"})
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTeam(@RequestBody RestUpdateTeamCommand command) throws NotFoundException, IllegalAccessException {
        teams.updateTeam(command.toCommand());
    }

    @Secured({"ROLE_USER"})
    @PutMapping("/{id}/members")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addMemberToTeam(@PathVariable Long id, @RequestBody RestMembersCommand command) throws NotFoundException, IllegalAccessException {
        teams.addMember(id, command.toCommands());
    }

    @PatchMapping("/{id}/task/{idTask}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addTaskToSprintBacklog(@PathVariable Long id, @PathVariable Long idTask) throws NotFoundException, IllegalAccessException {
        teams.addTask(id, idTask);
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
    private static class RestUpdateTeamCommand {
        Long id;
        String name;
        String accessCode;

        TeamsUseCase.UpdateTeamCommand toCommand() {
            return new TeamsUseCase.UpdateTeamCommand(id, name, accessCode);
        }
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
    private static class RestMembersCommand {
        Set<RestMemberCommand> members;

        Set<MemberCommand> toCommands() {
            Set<MemberCommand> listMembers = new HashSet<>();
            for(RestMemberCommand member: this.members) {
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
    @Getter
    @Setter
    @AllArgsConstructor
    private static class RestSprintBacklog {
        private List<TaskRequest> tasksPBI = new ArrayList<>();
        private List<TaskRequest> tasksTasks = new ArrayList<>();
        private List<TaskRequest> tasksInProgress = new ArrayList<>();
        private List<TaskRequest> tasksMergeRequest = new ArrayList<>();
        private List<TaskRequest> tasksDone = new ArrayList<>();

        public RestSprintBacklog(TaskToRestCommandConverter tasksConverter, List<Task> sprintBacklog) {
            this.sort(tasksConverter, sprintBacklog);
        }

        private void sort(TaskToRestCommandConverter tasksConverter, List<Task> sprintBacklog) {
            for(Task task: sprintBacklog) {
                tasksPBI.add(tasksConverter.toDto(task));
                for(Subtask subtask: task.getSubtasks()) {
                    switch (subtask.getStatusTask()) {
                        case NEW_TASK:
                            tasksTasks.add(tasksConverter.toDto(subtask));
                            break;
                        case IN_PROGRESS:
                            tasksInProgress.add(tasksConverter.toDto(subtask));
                            break;
                        case MERGE_REQUEST:
                            tasksMergeRequest.add(tasksConverter.toDto(subtask));
                            break;
                        case DONE:
                            tasksDone.add(tasksConverter.toDto(subtask));
                            break;
                    }
                }
            }
        }
    }
}
