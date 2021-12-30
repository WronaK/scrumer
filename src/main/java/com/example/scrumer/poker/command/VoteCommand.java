package com.example.scrumer.poker.command;

import lombok.Data;

@Data
public class VoteCommand {
    private String estimation;
    private Long idUser;
    private Long idTask;
    private String idScrumPoker;
}
