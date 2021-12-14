package com.example.scrumer.issue.controller;

import com.example.scrumer.issue.command.*;
import com.example.scrumer.issue.mapper.UserStoryMapper;
import com.example.scrumer.issue.service.useCase.UserStoryUseCase;
import com.example.scrumer.team.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/story")
public class UserStoryController {
    private final UserStoryUseCase userStoryUseCase;

    @GetMapping
    public List<UserStoryCommand> getAll() {
        return userStoryUseCase.findAll().stream()
                .map(UserStoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserStoryCommand getById(@PathVariable Long id) {
        return userStoryUseCase.findById(id).map(UserStoryMapper::toDto)
                .orElseThrow();
    }

    @PutMapping()
    public void updateUserStory(@RequestBody UpdateUserStoryCommand userStoryCommand) {
        userStoryUseCase.updateUserStory(userStoryCommand);
    }

    @PutMapping("/{id}/issue")
    public void addIssue(@PathVariable Long id,
                            @RequestBody CreateIssueCommand command) {
        userStoryUseCase.addIssue(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        userStoryUseCase.deleteById(id);
    }

    @PostMapping("/{id}/attachment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addAttachmentIssue(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        userStoryUseCase.addAttachment(id, file);
    }

    @PostMapping("/{idProject}/import")
    public void importUserStory(@PathVariable Long idProject, @RequestBody List<ImportUserStoryCommand> commands) {
        userStoryUseCase.importUserStories(idProject, commands);
    }
}
