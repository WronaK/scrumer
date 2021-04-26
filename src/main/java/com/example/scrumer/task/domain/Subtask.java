package com.example.scrumer.task.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    public Subtask(String title, String description, Integer priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }
}
