package com.example.scrumer.issue.command;

import com.example.scrumer.issue.entity.PriorityStatus;
import com.example.scrumer.issue.entity.StatusIssue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExportUserStoryCommand {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private String statusIssue;
    private Integer storyPoints;
    private String team;
}
