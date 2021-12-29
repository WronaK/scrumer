package com.example.scrumer.chat.service;

import com.example.scrumer.chat.command.CreateMessageCommand;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.mapper.MessageMapper;
import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.model.Message;
import com.example.scrumer.chat.model.Room;
import com.example.scrumer.chat.repository.jpa.ChannelRepository;
import com.example.scrumer.chat.repository.mongo.ChannelMongoRepository;
import com.example.scrumer.chat.repository.mongo.MessageMongoRepository;
import com.example.scrumer.chat.service.useCase.MessageUseCase;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService implements MessageUseCase {
    private final MessageMongoRepository messageRepository;
    private final ChannelMongoRepository channelMongoRepository;
    private final ChannelRepository channelRepository;

    @Override
    public Message createMessage(CreateMessageCommand command) {
        Optional<Channel> channel = channelRepository.findById(command.getChannelId());

        Message message = messageRepository.save(
                Message.builder()
                        .senderId(command.getSenderId())
                        .senderName(command.getSenderName())
                        .content(command.getContent())
                        .timestamp(new Date())
                        .build());

        if (channel.isPresent()) {
            Optional<Room> rooms = channelMongoRepository.findRoomById(channel.get().getIdChannel());
            rooms.ifPresent(room -> room.addMessage(message));
            channelMongoRepository.save(rooms.get());
        }

        return message;
    }

    @Override
    public MessageCommand findById(String id) throws NotFoundException {
        return messageRepository.findById(id)
                .map(MessageMapper::toMessageCommand)
                .orElseThrow(() -> new NotFoundException("Not found message with id: " + id));
    }
}
