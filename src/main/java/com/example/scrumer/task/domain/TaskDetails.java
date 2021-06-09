package com.example.scrumer.task.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class TaskDetails {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private Integer priority;

    private Integer storyPoints;
}
