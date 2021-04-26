package com.example.scrumer.project.domain;

import com.example.scrumer.task.domain.Task;
import com.example.scrumer.team.domain.Team;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String accessCode;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private List<Task> productBacklog;
    @ManyToMany
    @JoinTable
    @JsonIgnoreProperties("projects")
    private Set<Team> teams;

    public Project(String name, String accessCode) {
        this.name = name;
        this.accessCode = accessCode;
    }

    public void addTaskToProductBacklog(Task task) {
        productBacklog.add(task);
    }
}
