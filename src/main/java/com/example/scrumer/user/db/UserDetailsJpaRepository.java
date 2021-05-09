package com.example.scrumer.user.db;

import com.example.scrumer.user.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsJpaRepository extends JpaRepository<UserDetails, Long> {
}
