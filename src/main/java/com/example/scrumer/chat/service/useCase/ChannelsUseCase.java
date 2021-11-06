package com.example.scrumer.chat.service.useCase;

import com.example.scrumer.chat.command.CreateChannelCommand;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.model.Channel;

import java.util.List;

public interface ChannelsUseCase {
    void createChannel(CreateChannelCommand command, String userEmail);

    Channel findById(Long id);

    void createNotificationMessage(Long id, Long recipientId);

    List<MessageCommand> findMessagesById(Long id);

    void clearNotification(Long id, String userEmail);
}
