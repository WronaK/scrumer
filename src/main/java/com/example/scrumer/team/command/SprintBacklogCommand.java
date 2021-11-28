package com.example.scrumer.team.command;

import com.example.scrumer.issue.command.PBICommand;
import com.example.scrumer.issue.entity.UserStory;
import com.example.scrumer.issue.mapper.IssueMapper;
import com.example.scrumer.issue.entity.Issue;
import com.example.scrumer.issue.command.IssueCommand;
import com.example.scrumer.issue.mapper.UserStoryMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SprintBacklogCommand {
    private List<PBICommand> tasksPBI = new ArrayList<>();
    private List<IssueCommand> tasksToDo = new ArrayList<>();
    private List<IssueCommand> tasksInProgress = new ArrayList<>();
    private List<IssueCommand> tasksMergeRequest = new ArrayList<>();
    private List<IssueCommand> tasksDone = new ArrayList<>();

    public SprintBacklogCommand(List<UserStory> userStories, List<Issue> sprintBacklog) {
        this.tasksPBI = userStories.stream().map(UserStoryMapper::toPBICommand).collect(Collectors.toList());
        this.sort(sprintBacklog);
    }

    private void sort(List<Issue> sprintBacklog) {
        for(Issue issue: sprintBacklog) {
            switch(issue.getStatusIssue()) {
                case TO_DO:
                    tasksToDo.add(IssueMapper.toDto(issue));
                    break;
                case IN_PROGRESS:
                    tasksInProgress.add(IssueMapper.toDto(issue));
                    break;
                case MERGE_REQUEST:
                    tasksMergeRequest.add(IssueMapper.toDto(issue));
                    break;
                case COMPLETED:
                    tasksDone.add(IssueMapper.toDto(issue));
                    break;
            }

        }
    }
}
