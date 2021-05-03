package com.example.scrumer.user.db;

import com.example.scrumer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
