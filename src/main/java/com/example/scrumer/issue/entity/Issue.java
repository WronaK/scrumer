package com.example.scrumer.issue.entity;

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

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Issue {
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

    @Enumerated(value = EnumType.STRING)
    private TypeIssue typeIssue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_story_id")
    private UserStory userStory;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "assign_issue",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties("realizeIssues")
    private Set<User> users = new HashSet<>();

    public void addRealizeIssue(User user) {
        users.add(user);
        user.getRealizeIssues().add(this);
    }

    public void removeRealizeIssue(User user) {
        users.remove(user);
        user.getRealizeIssues().remove(this);
    }
}
