package com.example.scrumer.task.domain;

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
public class RealizeTask {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("realizeTasks")
    private Subtask subtask;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("realizeTasks")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private StatusTask state;
}
