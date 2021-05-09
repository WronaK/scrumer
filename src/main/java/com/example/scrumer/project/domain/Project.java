package com.example.scrumer.project.domain;

import com.example.scrumer.task.domain.Task;
import com.example.scrumer.team.domain.Team;
import com.example.scrumer.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String accessCode;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    private User creator;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private List<Task> productBacklog;

    @ManyToMany
    @JoinTable
    @JsonIgnoreProperties("projects")
    private Set<Team> teams;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Project(String name, String accessCode, String description) {
        this.name = name;
        this.accessCode = accessCode;
        this.description = description;
    }

    public void addCreator(User creator) {
        this.creator = creator;
    }

    public void addTaskToProductBacklog(Task task) {
        productBacklog.add(task);
    }

    public void addTeam(Team team) {
        teams.add(team);
    }
}
