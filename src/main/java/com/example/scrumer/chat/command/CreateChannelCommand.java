package com.example.scrumer.chat.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateChannelCommand {
    private String channelName;
    private List<String> members;
    private String channelType;
}
