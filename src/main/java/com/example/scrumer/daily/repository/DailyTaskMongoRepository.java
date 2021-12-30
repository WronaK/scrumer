package com.example.scrumer.daily.repository;

import com.example.scrumer.daily.model.DailyTask;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DailyTaskMongoRepository extends MongoRepository<DailyTask, String> {
}
