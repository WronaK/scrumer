package com.example.scrumer.chat.service;

import com.example.scrumer.chat.command.CreateChannelCommand;
import com.example.scrumer.chat.command.InformationCreatedChannelCommand;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.mapper.MessageMapper;
import com.example.scrumer.chat.model.*;
import com.example.scrumer.chat.repository.jpa.ChannelUsersJpaRepository;
import com.example.scrumer.chat.repository.jpa.ChannelRepository;
import com.example.scrumer.chat.repository.mongo.ChannelMongoRepository;
import com.example.scrumer.chat.service.useCase.ChannelsUseCase;
import com.example.scrumer.user.repository.UserJpaRepository;
import com.example.scrumer.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService implements ChannelsUseCase {
    private final ChannelRepository channelRepository;
    private final UserJpaRepository userRepository;
    private final ChannelUsersJpaRepository channelUserRepository;
    private final ChannelMongoRepository channelMongoRepository;

    @Override
    public InformationCreatedChannelCommand createChannel(CreateChannelCommand command, String loggedUserEmail) {
        InformationCreatedChannelCommand.InformationCreatedChannelCommandBuilder informationCreatedChannelCommandBuilder = InformationCreatedChannelCommand.builder();

        userRepository.findByEmail(loggedUserEmail).ifPresent(created -> {
            if (checkIsExistPrivateChannelOrIsGroupChannel(command, informationCreatedChannelCommandBuilder, created)) {
                Set<User> members = fetchMembersByEmail(command);

                Room room = channelMongoRepository.save(Room.builder().build());

                Channel channel = Channel.builder()
                    .idLastMessage(Strings.EMPTY)
                    .idChannel(room.getId())
                    .channelName(command.getChannelName())
                    .channelType(command.getChannelType())
                    .build();

                addedUserToChannel(channel, created, members);

                Channel savedChannel = channelRepository.save(channel);

                informationCreatedChannelCommandBuilder
                    .idChannel(savedChannel.getId())
                    .idChannelMongo(savedChannel.getIdChannel())
                    .channelName(savedChannel.getChannelName(loggedUserEmail));

            }
        });
        return informationCreatedChannelCommandBuilder.build();
    }

    private boolean checkIsExistPrivateChannelOrIsGroupChannel(CreateChannelCommand command, InformationCreatedChannelCommand.InformationCreatedChannelCommandBuilder builder, User sender) {
        return (command.getChannelType().equals(ChannelType.GROUP_CHANNEL) ||
                (command.getChannelType().equals(ChannelType.PRIVATE_MESSAGES) && !checkIsExistChannel(sender, command.getMembers().get(0), builder)));
    }

    private boolean checkIsExistChannel(User sender, Long idRecipient, InformationCreatedChannelCommand.InformationCreatedChannelCommandBuilder infoChannelBuilder) {
        for (ChannelUser channelUser: sender.getUserChannels()) {
            Channel channel = channelUser.getChannel();

            if (channel.getChannelType().equals(ChannelType.PRIVATE_MESSAGES)) {
                for (User user : channel.getRecipients(sender.getId())) {
                    if (user.getId().equals(idRecipient)) {
                        infoChannelBuilder.idChannel(channel.getId()).idChannelMongo(channel.getIdChannel()).channelName(user.getUserDetails().getUsername());
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void addedUserToChannel(Channel channel, User sender, Set<User> members) {
        ChannelUser channelUser = ChannelUser.builder()
                .numbersNewMessages(0L)
                .channel(channel)
                .user(sender)
                .build();

        channel.addChannelUser(channelUserRepository.save(channelUser));

        members.forEach(member ->
                channel.addChannelUser(channelUserRepository.save(new ChannelUser(channel, member))));
    }

    private Set<User> fetchMembersByEmail(CreateChannelCommand command) {
        return command.getMembers()
                .stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Not found user with id: " + id)))
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
        List<Message> messages = new ArrayList<>();
        Optional<Channel> channel = channelRepository.findById(id);

        channel.flatMap(value -> channelMongoRepository.findRoomById(value.getIdChannel())).ifPresent(room -> messages.addAll(room.getMessages()));
        return messages.stream()
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
