package com.example.scrumer.daily.repository;

import com.example.scrumer.daily.model.Daily;
import com.example.scrumer.daily.model.DailyTask;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DailyMongoRepository extends MongoRepository<Daily, String> {

    Optional<Daily> findDailyByIdTeamAndDateBetween(Long idTeam, Date start, Date stop);
//    List<DailyTask> findDailyTaskByTeamIdAndUserIdAndTimestampBetween(Long teamId, Long userId, Date startDate, Date stopDate);
}
