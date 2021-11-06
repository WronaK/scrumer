package com.example.scrumer.user.service.useCase;

import com.example.scrumer.chat.command.ChannelCommand;
import com.example.scrumer.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    User findById(Long id);

    void deleteById(Long id);

    List<User> findAll();

    Optional<User> findByEmail(String userEmail);

    List<ChannelCommand> getChannels(String email);

//    List<PrivateMessagesCommand> getPrivateMessages(String userEmail);
}
