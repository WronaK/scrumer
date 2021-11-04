package com.example.scrumer.chat.controller;

import com.example.scrumer.chat.command.CreateChannelCommand;
import com.example.scrumer.chat.service.useCase.ChannelsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelsController {
    private final ChannelsUseCase channelsUseCase;

    @PostMapping
    public void createChannel(@RequestBody CreateChannelCommand command) {
        channelsUseCase.createChannel(command, getUserEmail());
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
