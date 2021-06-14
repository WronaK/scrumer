package com.example.scrumer.task.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum PriorityStatus {
    HIGH_PRIORITY, SECOND_LEVEL_PRIORITY, LOW_PRIORITY;

    public static Optional<PriorityStatus> parseString(String value) {
        return Arrays.stream(values())
                .filter(status -> StringUtils.equalsIgnoreCase(status.name(), value))
                .findFirst();
    }
}
