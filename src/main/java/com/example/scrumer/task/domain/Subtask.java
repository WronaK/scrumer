package com.example.scrumer.task.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subtask {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_details_id", referencedColumnName = "id")
    private TaskDetails taskDetails;

    @Enumerated(value = EnumType.STRING)
    private StatusTask statusTask;
}
