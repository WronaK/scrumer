package com.example.scrumer.chat.repository.jpa;

import com.example.scrumer.chat.model.ChannelUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelUsersJpaRepository extends JpaRepository<ChannelUser, Long> {
    Optional<ChannelUser> findChannelUserByChannel_IdAndUser_Id(Long channelId, Long userId);
    Optional<ChannelUser> findChannelUserByChannel_IdAndUser_Email(Long channelId, String email);
}
