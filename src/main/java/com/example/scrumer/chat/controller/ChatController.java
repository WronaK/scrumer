package com.example.scrumer.chat.controller;

import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.model.ChatNotification;
import com.example.scrumer.chat.model.Message;
import com.example.scrumer.chat.model.MessageDto;
import com.example.scrumer.chat.repository.ChannelsRepository;
import com.example.scrumer.chat.repository.MessagesRepository;
import com.example.scrumer.chat.service.ChannelService;
import com.example.scrumer.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChannelService channelService;
    private final ChannelsRepository channelsRepository;
    private final MessagesRepository messagesRepository;

    @MessageMapping("/chat")
    @Transactional
    public void processMessage(@Payload MessageDto messageDto) {
        Message message = Message.builder()
                .content(messageDto.getContent()).build();
        Message savedMessage = messagesRepository.save(message);

        Channel channel = channelsRepository.findById(messageDto.getChannelId()).orElseThrow();

        channel.addMessage(savedMessage);
        channelsRepository.save(channel);

        User recipients = channel.getMembers().stream().filter(m -> !m.getEmail().equals(messageDto.getSenderEmail())).findFirst().orElseThrow();

        messagingTemplate.convertAndSendToUser(recipients.getId().toString(), "/queue/messages",
                new ChatNotification(
                        savedMessage.getId(),
                        1L));
    }
}
