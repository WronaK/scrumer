package com.example.scrumer.issue.entity;

import com.example.scrumer.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RealizeIssue {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("realizeIssue")
    private Issue issues;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("realizeIssue")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private StatusIssue state;
}
