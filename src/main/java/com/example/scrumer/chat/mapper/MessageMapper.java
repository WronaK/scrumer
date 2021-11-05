package com.example.scrumer.chat.mapper;

import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.model.Message;

public class MessageMapper {

    public static MessageCommand toMessageCommand(Message message) {
        return MessageCommand.builder()
                .senderName(message.getSenderName())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .build();
    }
}
