package com.example.scrumer.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;

    public UserDetails(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
