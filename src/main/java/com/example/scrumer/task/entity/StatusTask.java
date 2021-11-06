package com.example.scrumer.task.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum StatusTask {
    NEW, FOR_IMPLEMENTATION, NEW_TASK, IN_PROGRESS, MERGE_REQUEST, DONE;

    public static Optional<StatusTask> parseString(String value) {
        return Arrays.stream(values())
                .filter(status -> StringUtils.equalsIgnoreCase(status.name(), value))
                .findFirst();
    }
}
