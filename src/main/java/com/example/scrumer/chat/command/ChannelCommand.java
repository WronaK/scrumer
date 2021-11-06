package com.example.scrumer.chat.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChannelCommand {
    private Long idChannel;
    private String channelName;
    private String lastMessage;
    private String channelType;
    private Long numberNewMessage;
}
