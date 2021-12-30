package com.example.scrumer.poker.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationCommand {
    private final String method = "NOTIFICATION";
    private String idScrumPoker;
}
