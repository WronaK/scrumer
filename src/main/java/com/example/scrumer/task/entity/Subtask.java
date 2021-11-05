package com.example.scrumer.task.entity;

import com.example.scrumer.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subtask {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_details_id", referencedColumnName = "id")
    private TaskDetails taskDetails;

    @Enumerated(value = EnumType.STRING)
    private StatusTask statusTask;


    @OneToMany(mappedBy = "subtask")
    @JsonIgnoreProperties("subtask")
    private List<RealizeTask> realizeTasks = new ArrayList<>();

    public void addRealizeTask(RealizeTask realizeTask, User user) {
        if(this.realizeTasks == null) {
            this.realizeTasks = new ArrayList<>();
        }

        realizeTasks.add(realizeTask);
        user.getRealizeTasks().add(realizeTask);
    }
}