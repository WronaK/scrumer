package com.example.scrumer.team.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class TeamInformationCommand {
    private Long id;
    private String name;
    private String accessCode;
    private String description;
    private String username;
    private Long coverId;
}

