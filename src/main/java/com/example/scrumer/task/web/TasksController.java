package com.example.scrumer.task.web;

import com.example.scrumer.task.application.TasksService;
import com.example.scrumer.task.application.port.TasksUseCase;
import com.example.scrumer.task.application.port.TasksUseCase.CreateTaskCommand;
import com.example.scrumer.task.converter.TaskToTaskRequestConverter;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.task.request.TaskRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
public class TasksController {
    private final TasksService tasks;
    private final TaskToTaskRequestConverter tasksConverter;

    @GetMapping
    public List<TaskRequest> getAll() {
        return tasks.findAll().stream()
                .map(tasksConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskRequest getById(@PathVariable Long id) {
        Optional<Task> task = tasks.findById(id);
        return task.map(tasksConverter::toDto).orElseThrow(() -> new IllegalArgumentException("Not found task id: " + id));
    }

    @PutMapping()
    public void updateTaskById(@RequestBody TaskRequest task) {
        tasks.updateTask(task);
    }

    @PutMapping("/{id}/subtasks")
    public void addSubtask(@PathVariable Long id,
                           @RequestBody RestSubtasksCommand command) {
        tasks.addSubtask(id, command.toCommands());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tasks.deleteById(id);
    }


    @Data
    private static class RestSubtasksCommand {
        Set<RestSubtaskCommand> tasks;

        Set<CreateTaskCommand> toCommands() {
            Set<CreateTaskCommand> listTasks = new HashSet<>();
            for (RestSubtaskCommand task: this.tasks) {
                listTasks.add(task.toCreateCommand());
            }
            return listTasks;
        }
    }

    @Data
    private static class RestSubtaskCommand {
        private String title;
        private String description;
        private Integer priority;

        CreateTaskCommand toCreateCommand() {
            return new CreateTaskCommand(title, description, priority);
        }
    }
}
