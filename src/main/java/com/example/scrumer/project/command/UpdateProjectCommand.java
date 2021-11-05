package com.example.scrumer.project.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateProjectCommand {
    private Long id;
    private String name;
    private String accessCode;
    private String description;
    private String productOwner;
    private String scrumMaster;
}