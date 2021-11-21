package com.example.scrumer.upload.repository;

import com.example.scrumer.upload.entity.UploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<UploadEntity, Long> {
}
