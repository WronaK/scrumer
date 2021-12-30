package com.example.scrumer.chat.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class InformationCreatedChannelCommand {
    private Long idChannel;
    private String idChannelMongo;
    private String channelName;
}
