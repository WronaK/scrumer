package com.example.scrumer.chat.service;

import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.repository.ChannelsRepository;
import com.example.scrumer.chat.service.useCase.ChannelsUseCase;
import com.example.scrumer.user.repository.UserJpaRepository;
import com.example.scrumer.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelService implements ChannelsUseCase {
    private final ChannelsRepository channelsRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void createChannel(String recipientEmail, String userEmail) {
        Optional<User> recipientUser = userJpaRepository.findByEmail(recipientEmail);
        Optional<User> senderUser = userJpaRepository.findByEmail(userEmail);

        if (recipientUser.isPresent() && senderUser.isPresent()) {
            Channel channel = new Channel();
            channel.addMember(recipientUser.get());
            channel.addMember(senderUser.get());
            channelsRepository.save(channel);
        }
    }
}
