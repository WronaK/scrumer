package com.example.scrumer.daily.repository;

import com.example.scrumer.daily.model.DailyMember;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DailyMemberMongoRepository extends MongoRepository<DailyMember, String> {
}
