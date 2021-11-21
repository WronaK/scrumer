package com.example.scrumer.project.entity;

import com.example.scrumer.task.entity.Task;
import com.example.scrumer.team.entity.Team;
import com.example.scrumer.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    private String projectName;

    private String accessCode;

    private Long coverId;

    @Column(columnDefinition="text")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("teams")
    private User productOwner;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private List<Task> productBacklog = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "projects_teams",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    @JsonIgnoreProperties("projects")
    private Set<Team> teams = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void addTask(Task task) {
        productBacklog.add(task);
    }

    public void addTeam(Team team) {
        teams.add(team);
        team.getProjects().add(this);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
        team.getProjects().remove(this);
    }
}
