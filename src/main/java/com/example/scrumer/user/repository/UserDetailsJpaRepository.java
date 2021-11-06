package com.example.scrumer.user.repository;

import com.example.scrumer.user.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsJpaRepository extends JpaRepository<UserDetails, Long> {
}
