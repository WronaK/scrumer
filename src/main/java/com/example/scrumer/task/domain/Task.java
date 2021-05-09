package com.example.scrumer.task.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private Long id;
//    private String title;
//    private String description;
//    private Integer priority;
//    private Integer storyPoints;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_details_id", referencedColumnName = "id")
    private TaskDetails taskDetails;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private List<Subtask> subtasks;

    public Task(TaskDetails taskDetails) {
        this.taskDetails = taskDetails;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }
}
