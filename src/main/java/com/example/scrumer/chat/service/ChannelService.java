package com.example.scrumer.chat.service;

import com.example.scrumer.chat.command.CreateChannelCommand;
import com.example.scrumer.chat.command.InfoChannel;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.mapper.MessageMapper;
import com.example.scrumer.chat.model.*;
import com.example.scrumer.chat.repository.jpa.ChannelUsersJpaRepository;
import com.example.scrumer.chat.repository.jpa.ChannelRepository;
import com.example.scrumer.chat.repository.mongo.ChannelMongoRepository;
import com.example.scrumer.chat.repository.mongo.MessageMongoRepository;
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
    private final MessageMongoRepository messageRepository;
    private final ChannelUsersJpaRepository channelUserRepository;
    private final ChannelMongoRepository channelMongoRepository;

    @Override
    public InfoChannel createChannel(CreateChannelCommand command, String userEmail) {
        InfoChannel.InfoChannelBuilder infoChannel = InfoChannel.builder();

        userRepository.findByEmail(userEmail).ifPresent(sender -> {

            if (command.getChannelType().equals(ChannelType.GROUP_CHANNEL) || (command.getChannelType().equals(ChannelType.PRIVATE_MESSAGES) && !checkIsExistChannel(sender, command.getMembers().get(0), infoChannel))) {

                Set<User> members = fetchMembersByEmail(command);

                Room room = channelMongoRepository.save(Room.builder().build());

                Channel channel = Channel.builder()
                        .idLastMessage(Strings.EMPTY)
                        .idChannel(room.getId())
                        .channelName(command.getChannelName())
                        .channelType(command.getChannelType())
                        .build();

                ChannelUser channelUser = ChannelUser.builder()
                        .numbersNewMessages(0L)
                        .channel(channel)
                        .user(sender)
                        .build();

                channel.addChannelUser(channelUserRepository.save(channelUser));

                members.forEach(member ->
                        channel.addChannelUser(channelUserRepository.save(new ChannelUser(channel, member))));

                Channel channelS = channelRepository.save(channel);

                String channelName;
                if (channel.getChannelType() == ChannelType.GROUP_CHANNEL) {
                    channelName = channel.getChannelName();

                } else {
                    Optional<ChannelUser> channelUserRecipient = channel.getChannelUsers()
                            .stream()
                            .filter(c -> !c.getUser().getEmail().equals(userEmail))
                            .findFirst();
                    channelName = channelUserRecipient.get().getUser().getUserDetails().getUsername();
                }

                if (channelName.equals("")) {
                    channelName = "My private conversation";
                }


                infoChannel.idChannel(channelS.getId()).idChannelMongo(channelS.getIdChannel()).channelName(channelName);

            }
        });
        return infoChannel.build();
    }

    private boolean checkIsExistChannel(User sender, Long idRecipient, InfoChannel.InfoChannelBuilder infoChannelBuilder) {
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
