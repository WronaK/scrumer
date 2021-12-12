package com.example.scrumer.poker.command;

import lombok.Builder;
import lombok.Data;

@Data
public class ChangeEstimationStatusCommand {
    private Long idTask;
    private String idScrumPoker;
}
