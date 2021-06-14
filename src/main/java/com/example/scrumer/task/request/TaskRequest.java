package com.example.scrumer.task.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class TaskRequest {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private Integer storyPoints;
    private String status;
}
