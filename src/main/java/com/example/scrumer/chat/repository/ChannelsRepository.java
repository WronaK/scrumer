package com.example.scrumer.chat.repository;

import com.example.scrumer.chat.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelsRepository extends JpaRepository<Channel, Long> {
}
