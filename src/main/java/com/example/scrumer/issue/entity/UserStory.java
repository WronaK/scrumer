package com.example.scrumer.issue.entity;

import com.example.scrumer.team.entity.Team;
import com.example.scrumer.upload.entity.UploadEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserStory {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Column(columnDefinition="text")
    private String description;

    @Enumerated(value = EnumType.STRING)
    private PriorityStatus priority;

    private Integer storyPoints;

    @Enumerated(value = EnumType.STRING)
    private StatusIssue statusIssue;

    @OneToMany(mappedBy = "userStory")
    private List<Issue> issues = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UploadEntity> attachments;

    public void addAttachment(UploadEntity attachment) {
        this.attachments.add(attachment);
    }

    public void addIssue(Issue issue) {
        issues.add(issue);
        issue.setUserStory(this);
    }
}
