package com.example.scrumer.user.repository;

import com.example.scrumer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(
            " SELECT u from User u JOIN u.userDetails uD " +
                    " WHERE u.email LIKE CONCAT('%', :name, '%') " +
                    " OR uD.name LIKE CONCAT('%', :name, '%') " +
                    " OR uD.surname LIKE CONCAT('%', :name, '%') "
    )
    List<User> findUsersByName(String name);
}
