package com.example.scrumer.team.entity;

import com.example.scrumer.issue.entity.UserStory;
import com.example.scrumer.project.entity.Project;
import com.example.scrumer.issue.entity.Issue;
import com.example.scrumer.upload.entity.UploadEntity;
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
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "team")
    private List<UserStory> sprintBacklog = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private List<Issue> sprintBoard = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UploadEntity> attachments;

    public void addMember(User user) {
        members.add(user);
        user.getTeams().add(this);
    }

    public void addIssueToSprintBoard(Issue issue) {
        sprintBoard.add(issue);
    }

    public void addUserStoryToSprintBacklog(UserStory userStory) {
        sprintBacklog.add(userStory);
        userStory.setTeam(this);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.getTeams().remove(this);
    }

    public void addProject(Project project) {
        projects.add(project);
        project.getTeams().add(this);
    }

    public void addAttachment(UploadEntity attachment) {
        this.attachments.add(attachment);
    }

    public List<Member> getMembersTeam() {
        List<Member> collection = new ArrayList<>();


        members.forEach(member -> collection.add(new Member(member.getId(), member.getUserDetails().getUsername())));
        collection.add(new Member(scrumMaster.getId(), scrumMaster.getUserDetails().getUsername()));
        return collection;
    }
}
