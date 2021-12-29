package com.example.scrumer.chat.command;

import com.example.scrumer.chat.model.ChannelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateChannelCommand {
    private String channelName;
    private List<Long> members;
    private ChannelType channelType;
}
