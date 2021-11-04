package com.example.scrumer.chat.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Data
@Document
public class Message {
    @Id
    private String id;

    private Long senderId;

    private String senderName;

    private Long channelId;

    private String content;

    private Date timestamp;

    private MessageStatus status;
}
