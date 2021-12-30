package com.example.scrumer.daily.model;

import com.example.scrumer.chat.model.Message;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Data
@Document
public class DailyMember {
    @Id
    private String id;

    private Long idUser;

    @DBRef
    private List<DailyTask> tasks = new ArrayList<>();

    public void addDailyTask(DailyTask dailyTask) {
        tasks.add(dailyTask);
    }
}
