package com.example.scrumer.issue.mapper;

import com.example.scrumer.issue.command.ExportUserStoryCommand;
import com.example.scrumer.issue.command.PBICommand;
import com.example.scrumer.issue.command.UserStoryCommand;
import com.example.scrumer.issue.entity.UserStory;
import com.example.scrumer.user.command.AttachmentCommand;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;
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
    public static ExportUserStoryCommand toExportCommand(UserStory userStory) {
        ExportUserStoryCommand.ExportUserStoryCommandBuilder builder = ExportUserStoryCommand.builder();
        if (Objects.nonNull(userStory.getId())) {
            builder.id(userStory.getId());
        }

        if (Objects.nonNull(userStory.getTitle())) {
            builder.title(userStory.getTitle());
        }

        if (Objects.nonNull(userStory.getDescription())) {
            builder.title(userStory.getDescription());
        }

        if (Objects.nonNull(userStory.getStoryPoints())) {
            builder.storyPoints(userStory.getStoryPoints());
        }

        if (Objects.nonNull(userStory.getPriority())) {
            builder.priority(userStory.getPriority().name());
        }

        if (Objects.nonNull(userStory.getStatusIssue())) {
            builder.statusIssue(userStory.getStatusIssue().name());
        }

        if (Objects.nonNull(userStory.getTeam())) {
            builder.team(userStory.getTeam().getTeamName());
        }
        return builder.build();
    }
}
