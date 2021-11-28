package com.example.scrumer.issue.mapper;

import com.example.scrumer.issue.command.PBICommand;
import com.example.scrumer.issue.command.UserStoryCommand;
import com.example.scrumer.issue.entity.UserStory;
import com.example.scrumer.user.command.AttachmentCommand;
import org.apache.logging.log4j.util.Strings;

import java.util.stream.Collectors;

public class UserStoryMapper {
    public static UserStoryCommand toDto(UserStory userStory) {
        return UserStoryCommand.builder()
                .id(userStory.getId())
                .title(userStory.getTitle())
                .description(userStory.getDescription())
                .priority(userStory.getPriority())
                .statusIssue(userStory.getStatusIssue())
                .storyPoints(userStory.getStoryPoints())
                .team(userStory.getTeam() != null? userStory.getTeam().getTeamName() : Strings.EMPTY)
                .attachments(userStory.getAttachments().stream().map(attachment -> new AttachmentCommand(attachment.getId(), attachment.getFilename())).collect(Collectors.toList()))
                .build();
    }

    public static PBICommand toPBICommand(UserStory userStory) {
        return PBICommand.builder()
                .id(userStory.getId())
                .title(userStory.getTitle())
                .description(userStory.getDescription())
                .storyPoints(userStory.getStoryPoints())
                .statusIssue(userStory.getStatusIssue())
                .priority(userStory.getPriority())
                .attachments(userStory.getAttachments().stream().map(attachment -> new AttachmentCommand(attachment.getId(), attachment.getFilename())).collect(Collectors.toList()))
                .build();
    }
}
