package com.example.scrumer.chat.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDto {
    private Long idChannel;
    private String recipientName;
}
