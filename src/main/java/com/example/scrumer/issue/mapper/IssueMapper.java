package com.example.scrumer.issue.mapper;

import com.example.scrumer.issue.command.AssignCommand;
import com.example.scrumer.issue.entity.Issue;
import com.example.scrumer.issue.command.IssueCommand;
import com.example.scrumer.user.command.AttachmentCommand;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class IssueMapper {
    public static IssueCommand toDto(Issue task) {
        return IssueCommand.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .storyPoints(task.getStoryPoints() != null ? task.getStoryPoints(): null)
                .statusIssue(task.getStatusIssue())
                .idUserStory(task.getUserStory() != null ? task.getUserStory().getId() : null)
                .titleUserStory(task.getUserStory() != null ? task.getUserStory().getTitle() : Strings.EMPTY)
                .typeIssue(task.getTypeIssue())
                .assignCommands(task.getUsers().stream().map(user -> new AssignCommand(user.getImageId(), user.getId(), user.getUserDetails().getName(), user.getUserDetails().getSurname())).collect(Collectors.toList()))
                .attachments(task.getAttachments().stream().map(attachment -> new AttachmentCommand(attachment.getId(), attachment.getFilename())).collect(Collectors.toList()))
                .build();
    }
}
