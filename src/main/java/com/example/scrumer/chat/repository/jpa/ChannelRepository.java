package com.example.scrumer.chat.repository.jpa;

import com.example.scrumer.chat.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
