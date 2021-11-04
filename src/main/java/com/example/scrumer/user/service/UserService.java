package com.example.scrumer.user.service;

import com.example.scrumer.chat.command.ChannelCommand;
import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.model.ChannelType;
import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.repository.UserJpaRepository;
import com.example.scrumer.user.service.useCase.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserJpaRepository repository;

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ChannelCommand> getChannels(String email) {
        return findByEmail(email).get()
                .getChannels()
                .stream()
                .map(channel ->
                        getChannelCommand(channel, email))
                .collect(Collectors.toList());
    }

    public ChannelCommand getChannelCommand(Channel channel, String email) {
        String channelName = "";

        if (channel.getChannelType() == ChannelType.GROUP_CHANNEL) {
            channelName = channel.getChannelName();
        } else {
            for(User user: channel.getMembers()) {
                if (!user.getEmail().equals(email)) {
                    channelName = user.getUserDetails().getUsername();
                }
            }

            if (channelName.equals("")) {
                channelName = "My private conversation";
            }
        }

        return ChannelCommand.builder()
                .idChannel(channel.getId())
                .channelName(channelName)
                .lastMessage("Last message")
                .channelType(channel.getChannelType().name())
                .build();
    }

//    @Override
//    public List<PrivateMessagesCommand> getPrivateMessages(String userEmail) {
//        Optional<User> user = findByEmail(userEmail);
//        return user.get()
//                .getPrivateMessages()
//                .stream()
//                .map(privateMessages ->
//                        getToCommand(privateMessages, user.get().getId())
//                ).collect(Collectors.toList());
//    }
//
//    public PrivateMessagesCommand getToCommand(PrivateMessages privateMessages, Long id) {
//
//        return PrivateMessagesCommand.builder()
//                .conversationId(privateMessages.getConversationId())
//                .recipientName(privateMessages.getRecipientUsername())
//                .lastMessage("Last message....")
//                .build();
//    }


//    public ChannelCommand createChannelCommand(Channel channel) {
//
//    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return repository.findByEmail(userEmail);
    }
}
