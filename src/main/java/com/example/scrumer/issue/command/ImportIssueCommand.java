package com.example.scrumer.issue.command;

import com.example.scrumer.issue.entity.PriorityStatus;
import com.example.scrumer.issue.entity.StatusIssue;
import com.example.scrumer.issue.entity.TypeIssue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ImportIssueCommand {
    private String title;
    private String description;
    private PriorityStatus priority;
    private StatusIssue statusIssue;
    private TypeIssue typeIssue;
    private Set<Long> users;
    private Integer storyPoints;
}
