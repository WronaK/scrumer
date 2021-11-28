package com.example.scrumer.project.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectDetailsCommand {
    private Long id;
    private String name;
    private String description;
    private String username;
    private Long coverId;
}
