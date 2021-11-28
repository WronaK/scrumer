package com.example.scrumer.user.service.useCase;

import com.example.scrumer.chat.command.ChannelCommand;
import com.example.scrumer.user.command.SuggestedUserCommand;
import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.service.UserService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    User findById(Long id);

    void deleteById(Long id);

    List<User> findAll();

    Optional<User> findByEmail(String userEmail);

    List<ChannelCommand> getChannels(String email);

    List<SuggestedUserCommand> getUsers(String name);

    void updateImageProfile(MultipartFile file, String email);

//    List<PrivateMessagesCommand> getPrivateMessages(String userEmail);
}
