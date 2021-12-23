package com.example.scrumer.issue.service.useCase;

import com.example.scrumer.issue.command.CreateIssueCommand;
import com.example.scrumer.issue.command.UpdateUserStoryCommand;
import com.example.scrumer.issue.entity.UserStory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserStoryUseCase {
    Optional<UserStory> findById(Long id);

    List<UserStory> findAll();

    void deleteById(Long id);

    void addIssue(Long id, CreateIssueCommand command);

    void updateUserStory(UpdateUserStoryCommand userStoryCommand);

    void addAttachment(Long id, MultipartFile file);

    void setStoryPoints(Long id, String storyPoints);
}
