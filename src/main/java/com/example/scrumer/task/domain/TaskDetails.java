package com.example.scrumer.task.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(columnDefinition="text")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private PriorityStatus priority;

    private Integer storyPoints;
}
