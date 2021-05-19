package com.example.scrumer.task.web;

import com.example.scrumer.task.application.port.SubtasksUseCase;
import com.example.scrumer.task.domain.Subtask;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subtasks")
public class SubtasksController {
    private final SubtasksUseCase subtasks;

    @GetMapping("/{id}")
    public Optional<Subtask> getSubtaskById(@PathVariable Long id) {
        return subtasks.findById(id);
    }
}
