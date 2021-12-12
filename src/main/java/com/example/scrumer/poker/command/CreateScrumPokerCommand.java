package com.example.scrumer.poker.command;

import lombok.Data;

import java.util.List;

@Data
public class CreateScrumPokerCommand {
    private Long idCreator;
    private List<Long> invitedTeams;
    private List<Long> invitedMembers;
}
