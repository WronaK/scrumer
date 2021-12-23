package com.example.scrumer.poker.command;

import com.example.scrumer.poker.model.TeamVote;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class StopEstimationCommand {
    private final String method = "STOP";
    private Long idTask;
    private String idScrumPoker;
    private Set<TeamVote> estimation;
    private String resultEstimation;
}
