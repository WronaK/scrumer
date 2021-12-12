package com.example.scrumer.poker.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamVote {
    private Long idUser;
    private String estimation;
}
