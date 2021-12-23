package com.example.scrumer.poker.command;

import com.example.scrumer.poker.model.ScrumPokerStatus;
import com.example.scrumer.poker.model.TaskCommand;
import com.example.scrumer.poker.model.TeamVote;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class EventCommand {
    private String method;
    private String idScrumPoker;
    private Long idCreator;
    private Set<Long> members;
    private Set<TaskCommand> tasks;
    private ScrumPokerStatus scrumPokerStatus;
    private Set<TeamVote> individualEstimation;
    private Long currentTask;
}
