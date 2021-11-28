package com.example.scrumer.upload.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UploadEntity {
    @Id
    @GeneratedValue
    Long id;
    String filenameDysk;
    String contentType;
    String filename;
    String path;
    LocalDateTime cratedAt;
}
