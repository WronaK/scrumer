package com.example.scrumer.issue.service;

import com.example.scrumer.issue.command.CreateIssueCommand;
import com.example.scrumer.issue.command.UpdateUserStoryCommand;
import com.example.scrumer.issue.entity.Issue;
import com.example.scrumer.issue.entity.UserStory;
import com.example.scrumer.issue.repository.IssueJpaRepository;
import com.example.scrumer.issue.repository.UserStoryJpaRepository;
import com.example.scrumer.issue.service.useCase.UserStoryUseCase;
import com.example.scrumer.team.repository.TeamJpaRepository;
import com.example.scrumer.upload.command.SaveUploadCommand;
import com.example.scrumer.upload.entity.UploadEntity;
import com.example.scrumer.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserStoryService implements UserStoryUseCase {
    private final UserStoryJpaRepository userStoryJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final IssueJpaRepository issueJpaRepository;
    private final UploadService uploadUseCase;

    @Override
    public Optional<UserStory> findById(Long id) {
        return userStoryJpaRepository.findById(id);
    }

    @Override
    public List<UserStory> findAll() {
        return userStoryJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userStoryJpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addIssue(Long id, CreateIssueCommand command) {
        teamJpaRepository.findById(command.getIdTeam()).ifPresent(team -> {
            userStoryJpaRepository.findById(id)
                    .ifPresent(userStory -> {
                        Issue issue = Issue.builder()
                                .title(command.getTitle())
                                .description(command.getDescription())
                                .priority(command.getPriority())
                                .typeIssue(command.getTypeIssue())
                                .statusIssue(command.getStatusIssue())
                                .build();
                        Issue save = issueJpaRepository.save(issue);
                        userStory.addIssue(save);
                        team.addIssueToSprintBoard(save);
                    });
        });

    }

    @Override
    public void addAttachment(Long id, MultipartFile file) {
        userStoryJpaRepository.findById(id).ifPresent(userStory -> {
            try {
                UploadEntity savedUpload = uploadUseCase.save(new SaveUploadCommand(file.getOriginalFilename(), file.getBytes(), file.getContentType()));
                userStory.addAttachment(savedUpload);
                userStoryJpaRepository.save(userStory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setStoryPoints(Long id, String storyPoints) {
        userStoryJpaRepository.findById(id).ifPresent(userStory -> {
            userStory.setStoryPoints(Integer.valueOf(storyPoints));
            userStoryJpaRepository.save(userStory);
        });
    }

    @Override
    public void updateUserStory(UpdateUserStoryCommand userStoryCommand) {
        userStoryJpaRepository.findById(userStoryCommand.getId())
                .map(savedUserStory -> {
                    this.updateFields(userStoryCommand, savedUserStory);
                    return userStoryJpaRepository.save(savedUserStory);
                });
    }

    private void updateFields(UpdateUserStoryCommand userStoryCommand, UserStory savedUserStory) {
        if (userStoryCommand.getTitle() != null) {
            savedUserStory.setTitle(userStoryCommand.getTitle());
        }

        if (userStoryCommand.getDescription() != null) {
            savedUserStory.setDescription(userStoryCommand.getDescription());
        }

        if (userStoryCommand.getPriority() != null) {
            savedUserStory.setPriority(userStoryCommand.getPriority());
        }

        if (userStoryCommand.getStoryPoints() != null) {
            savedUserStory.setStoryPoints(userStoryCommand.getStoryPoints());
        }
    }
}
