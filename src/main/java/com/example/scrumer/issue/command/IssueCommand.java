package com.example.scrumer.issue.command;

import com.example.scrumer.issue.entity.PriorityStatus;
import com.example.scrumer.issue.entity.StatusIssue;
import com.example.scrumer.issue.entity.TypeIssue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class IssueCommand {
    private Long id;
    private String title;
    private String description;
    private PriorityStatus priority;
    private TypeIssue typeIssue;
    private Integer storyPoints;
    private StatusIssue statusIssue;
    private Long idUserStory;
    private String titleUserStory;
}
