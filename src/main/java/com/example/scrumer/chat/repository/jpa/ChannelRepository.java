package com.example.scrumer.chat.repository.jpa;

import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.model.ChannelType;
import com.example.scrumer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
