package com.example.scrumer.user.domain;

import com.example.scrumer.task.domain.RealizeTask;
import com.example.scrumer.team.domain.Team;
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
    @JsonIgnoreProperties("members")
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<RealizeTask> realizeTasks = new ArrayList<>();

    public User(String email, String password, UserDetails userDetails) {
        this.email = email;
        this.password = password;
        this.roles = Set.of("ROLE_USER");
        this.userDetails = userDetails;
    }

    public void addTeam(Team team) {
        if(this.teams == null){
            this.teams = new HashSet<>();
        }

        teams.add(team);
        team.getMembers().add(this);
    }
}

