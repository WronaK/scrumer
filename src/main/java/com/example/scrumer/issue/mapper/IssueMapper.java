package com.example.scrumer.issue.mapper;

import com.example.scrumer.issue.entity.Issue;
import com.example.scrumer.issue.command.IssueCommand;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

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
                .build();
    }
}
