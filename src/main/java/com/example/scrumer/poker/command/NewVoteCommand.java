package com.example.scrumer.poker.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewVoteCommand {
    private final String method = "VOTE";
    private String idScrumPoker;
    private Long idUser;
}
