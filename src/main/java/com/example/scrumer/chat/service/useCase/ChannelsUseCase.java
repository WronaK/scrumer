package com.example.scrumer.chat.service.useCase;

import com.example.scrumer.chat.command.CreateChannelCommand;
import com.example.scrumer.chat.model.Channel;

public interface ChannelsUseCase {
    void createChannel(CreateChannelCommand command, String userEmail);
    Channel findById(Long channelId);
}
