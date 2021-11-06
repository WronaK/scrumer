package com.example.scrumer.chat.model;

import com.example.scrumer.user.entity.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class ChannelUser {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private User user;

    private Long numbersNewMessages = 0L;

    public ChannelUser(Channel channel, User user) {
        this.channel = channel;
        this.user = user;
    }

    public void incrementNumbersNewMessages() {
        numbersNewMessages += 1;
    }

    public void clearNumberNewMessages() {
        numbersNewMessages = 0L;
    }
}
