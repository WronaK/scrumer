package com.example.scrumer.chat.service;

import com.example.scrumer.chat.command.CreateMessageCommand;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.mapper.MessageMapper;
import com.example.scrumer.chat.model.Message;
import com.example.scrumer.chat.repository.mongo.MessageMongoRepository;
import com.example.scrumer.chat.service.useCase.MessageUseCase;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MessageService implements MessageUseCase {
    private final MessageMongoRepository messageRepository;

    @Override
    public Message createMessage(CreateMessageCommand command) {
        return messageRepository.save(
                Message.builder()
                        .senderId(command.getSenderId())
                        .senderName(command.getSenderName())
                        .content(command.getContent())
                        .channelId(command.getChannelId())
                        .timestamp(new Date())
                        .build());
    }

    @Override
    public MessageCommand findById(String id) throws NotFoundException {
        return messageRepository.findById(id)
                .map(MessageMapper::toMessageCommand)
                .orElseThrow(() -> new NotFoundException("Not found message with id: " + id));
    }
}
