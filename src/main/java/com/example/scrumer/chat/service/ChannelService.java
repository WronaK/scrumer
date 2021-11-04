package com.example.scrumer.chat.service;

import com.example.scrumer.chat.command.CreateChannelCommand;
import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.model.ChannelType;
import com.example.scrumer.chat.model.Message;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.model.MessageStatus;
import com.example.scrumer.chat.repository.jpa.ChannelsRepository;
import com.example.scrumer.chat.repository.mongo.MessageMongoRepository;
import com.example.scrumer.chat.service.useCase.ChannelsUseCase;
import com.example.scrumer.user.repository.UserJpaRepository;
import com.example.scrumer.user.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService implements ChannelsUseCase {
    private final ChannelsRepository channelsRepository;
    private final UserJpaRepository userJpaRepository;
    private final MessageMongoRepository messageMongoRepository;

    @Override
    public void createChannel(CreateChannelCommand command, String userEmail) {
        Set<User> members = getMembers(command);
        Optional<User> senderUser = userJpaRepository.findByEmail(userEmail);

        if (senderUser.isPresent()) {
            Channel channel = new Channel();
            channel.setChannelName(command.getChannelName());
            channel.setChannelType(ChannelType.parseString(command.getChannelType()).get());
            channel.addMember(senderUser.get());

            for(User user: members) {
                channel.addMember(user);
            }
            channelsRepository.save(channel);
        }
    }

    private Set<User> getMembers(CreateChannelCommand command) {
        return command.getMembers().stream().map(member -> userJpaRepository.findByEmail(member).get()).collect(Collectors.toSet());
    }

    @Override
    public Channel findById(Long channelId) {
        return channelsRepository.findById(channelId).orElseThrow(() -> new NotFoundException("Not found channel with idL " + channelId));
    }

    public Message saveNewMessage(MessageCommand command) {
        Message message = Message.builder()
                .senderId(command.getSenderId())
                .senderName(command.getSenderName())
                .content(command.getContent())
                .status(MessageStatus.RECEIVED)
                .channelId(command.getChannelId())
                .timestamp(new Date())
                .build();
        return messageMongoRepository.save(message);
    }
}
