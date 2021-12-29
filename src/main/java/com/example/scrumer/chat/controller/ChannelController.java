package com.example.scrumer.chat.controller;

import com.example.scrumer.chat.command.CreateChannelCommand;
import com.example.scrumer.chat.command.CreateMessageCommand;
import com.example.scrumer.chat.command.InfoChannel;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.model.ChatNotification;
import com.example.scrumer.chat.model.Message;
import com.example.scrumer.chat.service.useCase.ChannelsUseCase;
import com.example.scrumer.chat.service.useCase.MessageUseCase;
import com.example.scrumer.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelsUseCase channelsUseCase;
    private final MessageUseCase messageUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public InfoChannel createChannel(@RequestBody CreateChannelCommand command) {
        return channelsUseCase.createChannel(command, getUserEmail());
    }

    @GetMapping("/{id}/messages")
    public List<MessageCommand> findMessagesById(@PathVariable Long id) {
        return channelsUseCase.findMessagesById(id);
    }

    @PostMapping("/{id}/notification/{recipientId}")
    public void createNotificationMessage(@PathVariable Long id, @PathVariable Long recipientId) {
        channelsUseCase.createNotificationMessage(id, recipientId);
    }

    @PostMapping("/{id}/clear/notification")
    public void clearNotification(@PathVariable Long id) {
        channelsUseCase.clearNotification(id, getUserEmail());
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody CreateMessageCommand messageCommand) {
        Channel channel = channelsUseCase.findById(messageCommand.getChannelId());
        List<User> recipients = channel.getRecipients(messageCommand.getSenderId());

        Message createdMessage = messageUseCase.createMessage(messageCommand);
        channel.setIdLastMessage(createdMessage.getId());

        recipients.forEach(recipient -> {
            messagingTemplate.convertAndSendToUser(recipient.getId().toString(), "/queue/chat",
                    new ChatNotification(
                            channel.getId(),
                            createdMessage.getId()));
            channelsUseCase.createNotificationMessage(channel.getId(), recipient.getId());
        });
    }
}
