package com.example.scrumer.user.service;

import com.example.scrumer.chat.model.ChannelDto;
import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.repository.UserJpaRepository;
import com.example.scrumer.user.service.useCase.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserJpaRepository repository;

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ChannelDto> getChannels(Long id) {
        return repository.findById(id).get().getChannels().stream().map(channel -> ChannelDto.builder().idChannel(channel.getId()).recipientName(channel.getChannelName()).build()).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return repository.findByEmail(userEmail);
    }
}
