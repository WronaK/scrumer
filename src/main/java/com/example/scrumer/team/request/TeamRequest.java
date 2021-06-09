package com.example.scrumer.team.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeamRequest {
    private Long id;
    private String name;
}
