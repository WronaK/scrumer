package com.example.scrumer.chat.controller;

import com.example.scrumer.chat.command.CreateMessageCommand;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.service.useCase.MessageUseCase;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageUseCase messageUseCase;

    @PostMapping
    public void createMessage(@RequestBody CreateMessageCommand command) {
        messageUseCase.createMessage(command);
    }

    @GetMapping("/{id}")
    public MessageCommand findById(@PathVariable String id) throws NotFoundException {
        return messageUseCase.findById(id);
    }
}
