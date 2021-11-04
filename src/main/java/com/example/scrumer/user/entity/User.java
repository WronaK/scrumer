package com.example.scrumer.user.entity;

import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.task.entity.RealizeTask;
import com.example.scrumer.team.entity.Team;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_details_id", referencedColumnName = "id")
    private UserDetails userDetails;

    @CollectionTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user")
    )
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    @ManyToMany(mappedBy = "members", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("members")
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnoreProperties("user")
    private List<RealizeTask> realizeTasks = new ArrayList<>();

//    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinColumn(name = "sender_id")
//    private List<PrivateMessages> privateMessages = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    private Set<Channel> channels = new HashSet<>();

    public User(String email, String password, UserDetails userDetails) {
        this.email = email;
        this.password = password;
        this.roles = Set.of("ROLE_USER");
        this.userDetails = userDetails;
    }

//    public void addPrivateMessage(PrivateMessages privateMessages) {
//        this.privateMessages.add(privateMessages);
//    }
}

