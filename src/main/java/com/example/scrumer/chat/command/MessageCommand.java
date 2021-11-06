package com.example.scrumer.chat.command;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageCommand {
    private String content;
    private Long senderId;
    private String senderName;
}
