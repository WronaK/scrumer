package com.example.scrumer.chat.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum ChannelType {
    PRIVATE_MESSAGES, GROUP_CHANNEL;

    public static Optional<ChannelType> parseString(String value) {
        return Arrays.stream(values())
                .filter(type -> StringUtils.equalsIgnoreCase(type.name(), value))
                .findFirst();
    }
}
