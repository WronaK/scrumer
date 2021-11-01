package com.example.scrumer.chat.model;

import com.example.scrumer.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Channel {
    @Id
    @GeneratedValue
    private Long id;

    private String channelName = "test name";

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "channels_members",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members = new HashSet<>();

    @OneToMany(
            mappedBy = "channel",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages = new ArrayList<>();

    public void addMember(User user) {
        this.members.add(user);
        user.getChannels().add(this);
    }

    public void removeMember(User user) {
        this.members.remove(user);
        user.getChannels().remove(this);
    }

    public void addMessage(Message message) {
        messages.add(message);
        message.setChannel(this);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
        message.setChannel(null);
    }
}
