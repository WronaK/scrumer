package com.example.scrumer.project.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CreateProjectCommand {
    private String name;
    private String accessCode;
    private String description;
    private String productOwner;
}
