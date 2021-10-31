package com.example.scrumer.chat.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @Email
    private String senderEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    private Channel channel;

    private String content;
    private MessageStatus status;
}
