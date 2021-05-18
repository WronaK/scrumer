package com.example.scrumer.project.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectRequest {
    private Long id;
    private String name;
    private String description;
    private String creatorName;
    private String productOwnerName;
    private String scrumMasterName;
}
