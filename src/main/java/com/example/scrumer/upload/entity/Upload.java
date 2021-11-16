package com.example.scrumer.upload.entity;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Upload {
    String id;
    byte[] file;
    String contentType;
    String filename;
    LocalDateTime cratedAt;
}
