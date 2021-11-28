package com.example.scrumer.issue.command;

import com.example.scrumer.issue.entity.PriorityStatus;

import com.example.scrumer.issue.entity.StatusIssue;
import com.example.scrumer.user.command.AttachmentCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserStoryCommand {
    private Long id;
    private String title;
    private String description;
    private PriorityStatus priority;
    private StatusIssue statusIssue;
    private Integer storyPoints;
    private String team;
    private List<AttachmentCommand> attachments;
}
