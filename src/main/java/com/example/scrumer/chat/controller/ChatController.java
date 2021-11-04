package com.example.scrumer.chat.controller;

import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.model.ChatNotification;
import com.example.scrumer.chat.model.Message;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.service.ChannelService;
import com.example.scrumer.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChannelService channelService;

    @MessageMapping("/chat")
    @Transactional
    public void processMessage(@Payload MessageCommand messageDto) {
        Channel channel = channelService.findById(messageDto.getChannelId());

        Message saved = channelService.saveNewMessage(messageDto);

        List<User> recipients = channel.getMembers().stream().filter(m -> !Objects.equals(m.getId(), messageDto.getSenderId())).collect(Collectors.toList());

        recipients.forEach(recipient ->  messagingTemplate.convertAndSendToUser(recipient.getId().toString(), "/queue/messages",
                new ChatNotification(
                        saved.getChannelId(),
                        saved.getSenderId(),
                        saved.getSenderName())));
    }
}
