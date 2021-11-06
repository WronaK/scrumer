package com.example.scrumer.chat.service.useCase;

import com.example.scrumer.chat.command.CreateMessageCommand;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.model.Message;
import javassist.NotFoundException;

public interface MessageUseCase {
    Message createMessage(CreateMessageCommand command);

    MessageCommand findById(String id) throws NotFoundException;
}
