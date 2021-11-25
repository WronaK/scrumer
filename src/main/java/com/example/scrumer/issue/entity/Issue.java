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
import java.util.List;

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

    @OneToMany(mappedBy = "issues")
    @JsonIgnoreProperties("issues")
    private List<RealizeIssue> realizeIssues = new ArrayList<>();

    public void addRealizeIssue(RealizeIssue realizeIssue, User user) {
        realizeIssues.add(realizeIssue);
        user.getRealizeIssues().add(realizeIssue);
    }
}
