package com.example.scrumer.user.service.useCase;

import com.example.scrumer.chat.model.ChannelDto;
import com.example.scrumer.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    Optional<User> findById(Long id);

    void deleteById(Long id);

    List<User> findAll();

    Optional<User> findByEmail(String userEmail);

    List<ChannelDto> getChannels(Long id);
}
