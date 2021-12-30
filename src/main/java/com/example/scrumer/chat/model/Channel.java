package com.example.scrumer.chat.model;

import com.example.scrumer.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.*;
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

    private String idChannel;

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

    public String getChannelName(String senderUser) {
        String channelName;
        if (this.getChannelType() == ChannelType.GROUP_CHANNEL) {
            channelName = this.getChannelName();

        } else {
            Optional<ChannelUser> channelUserRecipient = this.getChannelUsers()
                    .stream()
                    .filter(c -> !c.getUser().getEmail().equals(senderUser))
                    .findFirst();
            channelName = channelUserRecipient.get().getUser().getUserDetails().getUsername();
        }

        if (channelName.equals("")) {
            channelName = "My private conversation";
        }

        return channelName;
    }
}
