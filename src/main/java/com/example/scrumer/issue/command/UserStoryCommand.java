package com.example.scrumer.issue.command;

import com.example.scrumer.issue.entity.PriorityStatus;

import com.example.scrumer.issue.entity.StatusIssue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
}
