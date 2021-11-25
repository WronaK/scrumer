package com.example.scrumer.issue.command;

import com.example.scrumer.issue.entity.PriorityStatus;
import com.example.scrumer.issue.entity.StatusIssue;
import com.example.scrumer.issue.entity.TypeIssue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateIssueCommand {
    private Long idTeam;
    private String title;
    private String description;
    private PriorityStatus priority;
    private StatusIssue statusIssue;
    private TypeIssue typeIssue;
}
