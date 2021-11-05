package com.example.scrumer.chat.model;

import com.example.scrumer.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    private String channelName;

    @OneToMany(mappedBy = "channel")
    private Set<ChannelUser> channelUsers = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private ChannelType channelType;

    private String idLastMessage;

    public void addChannelUser(ChannelUser channelUser) {
        if (this.channelUsers == null) {
            this.channelUsers = new HashSet<>();
        }

        this.channelUsers.add(channelUser);
        channelUser.getUser().getUserChannels().add(channelUser);
    }

    public List<User> getRecipients(Long senderId) {
        return channelUsers.stream()
                .map(ChannelUser::getUser)
                .filter(channelUser -> !Objects.equals(channelUser.getId(), senderId))
                .collect(Collectors.toList());
    }
}
