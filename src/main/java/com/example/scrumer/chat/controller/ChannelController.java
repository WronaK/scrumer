package com.example.scrumer.chat.controller;

import com.example.scrumer.chat.command.CreateChannelCommand;
import com.example.scrumer.chat.command.MessageCommand;
import com.example.scrumer.chat.service.useCase.ChannelsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelsUseCase channelsUseCase;

    @PostMapping
    public void createChannel(@RequestBody CreateChannelCommand command) {
        channelsUseCase.createChannel(command, getUserEmail());
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

}
