package com.example.scrumer.team.domain;

import com.example.scrumer.project.domain.Project;
import com.example.scrumer.task.domain.Task;
import com.example.scrumer.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String accessCode;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "teams")
    @JsonIgnoreProperties("teams")
    private Set<User> members;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "teams")
    @JsonIgnoreProperties("teams")
    private Set<Project> projects;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private List<Task> sprintBoard;

    public Team(String name, String accessCode) {
        this.name = name;
        this.accessCode = accessCode;
    }

    public void addMember(User user) {
        members.add(user);
    }
}
