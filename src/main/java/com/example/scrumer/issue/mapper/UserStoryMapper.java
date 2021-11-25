package com.example.scrumer.issue.mapper;

import com.example.scrumer.issue.command.PBICommand;
import com.example.scrumer.issue.command.UserStoryCommand;
import com.example.scrumer.issue.entity.UserStory;
import org.apache.logging.log4j.util.Strings;

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
                .build();
    }
}
