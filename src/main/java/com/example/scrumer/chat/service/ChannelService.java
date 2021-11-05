package com.example.scrumer.chat.service;

import com.example.scrumer.chat.command.CreateChannelCommand;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.mapper.MessageMapper;
import com.example.scrumer.chat.model.*;
import com.example.scrumer.chat.repository.jpa.ChannelUsersJpaRepository;
import com.example.scrumer.chat.repository.jpa.ChannelRepository;
import com.example.scrumer.chat.repository.mongo.MessageMongoRepository;
import com.example.scrumer.chat.service.useCase.ChannelsUseCase;
import com.example.scrumer.user.repository.UserJpaRepository;
import com.example.scrumer.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService implements ChannelsUseCase {
    private final ChannelRepository channelRepository;
    private final UserJpaRepository userRepository;
    private final MessageMongoRepository messageRepository;
    private final ChannelUsersJpaRepository channelUserRepository;

    @Override
    public void createChannel(CreateChannelCommand command, String userEmail) {
        Set<User> members = fetchMembersByEmail(command);

        userRepository.findByEmail(userEmail).ifPresent(sender -> {
            Channel channel = Channel.builder()
                    .idLastMessage(Strings.EMPTY)
                    .channelName(command.getChannelName())
                    .channelType(ChannelType.parseString(command.getChannelType()).orElseThrow())
                    .build();

            ChannelUser channelUser = ChannelUser.builder()
                    .numbersNewMessages(0L)
                    .channel(channel)
                    .user(sender)
                    .build();

            channel.addChannelUser(channelUserRepository.save(channelUser));

            members.forEach(member ->
                    channel.addChannelUser(channelUserRepository.save(new ChannelUser(channel, member))));

            channelRepository.save(channel);
        });
    }

    private Set<User> fetchMembersByEmail(CreateChannelCommand command) {
        return command.getMembers()
                .stream()
                .map(email -> userRepository.findByEmail(email)
                        .orElseThrow(() -> new NotFoundException("Not found user with email: " + email)))
                .collect(Collectors.toSet());
    }

    @Override
    public Channel findById(Long channelId) {
        return channelRepository
                .findById(channelId)
                .orElseThrow(() -> new NotFoundException("Not found channel with idL " + channelId));
    }

    @Override
    public void createNotificationMessage(Long channelId, Long recipientId) {
        ChannelUser channelUser = channelUserRepository
                .findChannelUserByChannel_IdAndUser_Id(channelId, recipientId)
                .orElseThrow(() -> new NotFoundException("Not found channel-user with channel id: " + channelId + " and recipient id: " + recipientId));

        channelUser.incrementNumbersNewMessages();
        channelUserRepository.save(channelUser);
    }

    @Override
    public List<MessageCommand> findMessagesById(Long id) {
        return messageRepository
                .findMessageByChannelId(id)
                .stream()
                .map(MessageMapper::toMessageCommand).collect(Collectors.toList());
    }

    @Override
    public void clearNotification(Long channelId, String userEmail) {
        ChannelUser channelUser = channelUserRepository
                .findChannelUserByChannel_IdAndUser_Email(channelId, userEmail)
                .orElseThrow(() -> new NotFoundException("Not found channel-user with channel id: " + channelId + " and user email: " + userEmail));

        channelUser.clearNumberNewMessages();
        channelUserRepository.save(channelUser);
    }
}
