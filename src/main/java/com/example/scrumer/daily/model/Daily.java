package com.example.scrumer.daily.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Data
@Document
public class Daily {
    @Id
    private String id;

    private Long idTeam;

    private Date date;

    @DBRef
    private List<DailyMember> dailyMembers = new ArrayList<>();

    public void addDailyMembers(DailyMember dailyMember) {
        dailyMembers.add(dailyMember);
    }
}
