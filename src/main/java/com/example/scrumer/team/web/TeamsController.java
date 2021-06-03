package com.example.scrumer.team.web;

import com.example.scrumer.project.converter.ProjectToProjectRequestConverter;
import com.example.scrumer.project.request.ProjectShortcutRequest;
import com.example.scrumer.task.converter.TaskToTaskRequestConverter;
import com.example.scrumer.task.domain.Subtask;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.request.TaskRequest;
import com.example.scrumer.team.application.port.TeamsUseCase;
import com.example.scrumer.team.application.port.TeamsUseCase.CreateTeamCommand;
import com.example.scrumer.team.application.port.TeamsUseCase.MemberCommand;
import com.example.scrumer.team.application.port.TeamsUseCase.ProjectCommand;
import com.example.scrumer.team.converter.TeamToTeamRequestConverter;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.team.request.TeamDetails;
import com.example.scrumer.team.request.TeamRequest;
import com.example.scrumer.user.converter.UserToUserRequestConverter;
import com.example.scrumer.user.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
    private final TaskToTaskRequestConverter taskConverter;
    private final UserToUserRequestConverter userConverter;
    private final ProjectToProjectRequestConverter projectConverter;

    @GetMapping
    public List<TeamRequest> getAll() {
        return teams.findByUser(this.getUserEmail())
                .stream()
                .map(this.teamConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/projects")
    public List<ProjectShortcutRequest> getProjects(@PathVariable Long id) {
        return teams.findProjectsById(id).stream()
                .map(projectConverter::toDtoShortcut)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/members")
    public List<UserRequest> getMembers(@PathVariable Long id) {
        return teams.findMembersById(id).stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TeamDetails getTeamById(@PathVariable Long id) {
        return teams.findById(id).map(teamConverter::toDtoDetails).orElseThrow(() -> new IllegalArgumentException("Not found team id: " + id));
    }

    @GetMapping("/{id}/sprint_backlog")
    public RestSprintBacklog getSprintBacklogById(@PathVariable Long id) {
        return new RestSprintBacklog(taskConverter, teams.getSprintBacklog(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addTeam(@RequestBody RestCreateTeamCommand command) {
        Team team = teams.addTeam(command.toCommand(), getUserEmail());
        return ResponseEntity.created(createdTeamUri(team)).build();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTeam(@RequestBody RestUpdateTeamCommand command) {
        teams.updateTeam(command.toCommand());
    }

    @PutMapping("/{id}/members")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addMemberToTeam(@PathVariable Long id, @RequestBody RestMembersCommand command) {
        teams.addMember(id, command.toCommands());
    }

    @PutMapping("/{id}/projects")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addProjectToTeam(@PathVariable Long id, @RequestBody RestProjectCommand command) {
        teams.addProjectToTeam(id, command.toCommand());
    }

    @PatchMapping("/{id}/task/{idTask}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addTaskToSprintBacklog(@PathVariable Long id, @PathVariable Long idTask) {
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
    private static class RestProjectCommand {
        String name;
        String accessCode;

        ProjectCommand toCommand() {
            return new ProjectCommand(name, accessCode);
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

        public RestSprintBacklog(TaskToTaskRequestConverter tasksConverter, List<Task> sprintBacklog) {
            this.sort(tasksConverter, sprintBacklog);
        }

        private void sort(TaskToTaskRequestConverter tasksConverter, List<Task> sprintBacklog) {
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
