package com.example.scrumer.project.domain;

import com.example.scrumer.task.domain.Task;
import com.example.scrumer.team.domain.Team;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    private List<Team> teams;

    public Project(String name, String accessCode) {
        this.name = name;
        this.accessCode = accessCode;
        this.productBacklog = new ArrayList<>();
        this.teams = new ArrayList<>();
    }
}
