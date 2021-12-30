package com.example.scrumer.poker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamVote {
    private Long idUser;
    private String estimation;
}
