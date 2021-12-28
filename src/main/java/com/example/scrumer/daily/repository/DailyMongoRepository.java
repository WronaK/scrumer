package com.example.scrumer.daily.repository;

import com.example.scrumer.daily.model.DailyTask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface DailyMongoRepository extends MongoRepository<DailyTask, String> {

    List<DailyTask> findDailyTaskByTeamIdAndUserIdAndTimestampBetween(Long teamId, Long userId, Date startDate, Date stopDate);
}
