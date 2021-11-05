package com.example.scrumer.team.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeamDetailsCommand {
    private Long id;
    private String name;
    private String accessCode;
    private String creatorName;
}