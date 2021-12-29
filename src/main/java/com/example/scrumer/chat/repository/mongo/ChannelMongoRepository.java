package com.example.scrumer.chat.repository.mongo;

import com.example.scrumer.chat.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChannelMongoRepository extends MongoRepository<Room, String> {
    Optional<Room> findRoomById(String id);
}
