package com.example.scrumer.daily.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Data
@Document
public class DailyTask {
    @Id
    private String id;

    private String titleTask;

    private TypeTask typeTask;

    private Long taskId;

    private Date timestamp;
}
