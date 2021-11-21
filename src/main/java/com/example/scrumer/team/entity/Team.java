package com.example.scrumer.team.entity;

import com.example.scrumer.project.entity.Project;
import com.example.scrumer.task.entity.Task;
import com.example.scrumer.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String teamName;

    private String accessCode;

    private Long coverId;

    @Column(columnDefinition="text")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("teams")
    private User scrumMaster;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "teams_members",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    @JsonIgnoreProperties("teams")
    private Set<User> members = new HashSet<>();

    @ManyToMany(mappedBy = "teams", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("teams")
    private Set<Project> projects = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private List<Task> sprintBoard;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void addMember(User user) {
        members.add(user);
        user.getTeams().add(this);
    }

    public void addTaskToSprintBacklog(Task task) {
        sprintBoard.add(task);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.getTeams().remove(this);
    }
}
