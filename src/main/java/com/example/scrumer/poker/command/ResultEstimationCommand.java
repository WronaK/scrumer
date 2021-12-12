package com.example.scrumer.poker.command;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ResultEstimationCommand {
    private Long idTask;
    private String idScrumPoker;
    private Set<TeamVote> estimation;
    private String resultEstimation;
}
