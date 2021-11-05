package com.example.scrumer.chat.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageCommand {
    private Long channelId;
    private String content;
    private Long senderId;
    private String senderName;
}
