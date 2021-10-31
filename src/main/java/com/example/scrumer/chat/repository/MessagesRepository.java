package com.example.scrumer.chat.repository;

import com.example.scrumer.chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Message, Long> {
}
