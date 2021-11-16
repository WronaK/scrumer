package com.example.scrumer.upload.command;

import lombok.Value;

@Value
public class SaveUploadCommand {
    String filename;
    byte[] file;
    String contentType;
}
