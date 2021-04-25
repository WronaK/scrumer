package com.example.scrumer.task.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Subtask {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Integer priority;
    private Integer storyPoints;
    @Enumerated(value = EnumType.STRING)
    private StatusTask statusTask = StatusTask.PBI;

    public Subtask(String title, String description, Integer priority, Integer storyPoints) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.storyPoints = storyPoints;
    }
}
