package com.example.scrumer.websocket.controller;

import com.example.scrumer.chat.model.ChatNotification;
import com.example.scrumer.chat.command.CreateMessageCommand;
import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.model.Message;
import com.example.scrumer.chat.service.ChannelService;
import com.example.scrumer.chat.service.useCase.MessageUseCase;
import com.example.scrumer.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProcessMessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChannelService channelService;
    private final MessageUseCase messageUseCase;

    @MessageMapping("/chat")
    @Transactional
    public void processMessage(@Payload CreateMessageCommand messageCommand) {
        Channel channel = channelService.findById(messageCommand.getChannelId());
        List<User> recipients = channel.getRecipients(messageCommand.getSenderId());

        Message createdMessage = messageUseCase.createMessage(messageCommand);
        channel.setIdLastMessage(createdMessage.getId());

        recipients.forEach(recipient -> {
            messagingTemplate.convertAndSendToUser(recipient.getId().toString(), "/queue/messages",
                    new ChatNotification(
                            createdMessage.getChannelId(),
                            createdMessage.getId()));
            channelService.createNotificationMessage(createdMessage.getChannelId(), recipient.getId());
        });
    }
}
