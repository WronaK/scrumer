package com.example.scrumer.chat.repository.mongo;

import com.example.scrumer.chat.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageMongoRepository extends MongoRepository<Message, String> {

    List<Message> findMessageByChannelId(Long channelId);
}
