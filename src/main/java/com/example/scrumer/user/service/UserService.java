package com.example.scrumer.user.service;

import com.example.scrumer.chat.command.ChannelCommand;
import com.example.scrumer.chat.model.Channel;
import com.example.scrumer.chat.model.ChannelType;
import com.example.scrumer.chat.model.ChannelUser;
import com.example.scrumer.chat.model.Message;
import com.example.scrumer.chat.repository.mongo.MessageMongoRepository;
import com.example.scrumer.upload.command.SaveUploadCommand;
import com.example.scrumer.upload.entity.UploadEntity;
import com.example.scrumer.upload.service.useCase.UploadUseCase;
import com.example.scrumer.user.command.SuggestedUserCommand;
import com.example.scrumer.user.entity.User;
import com.example.scrumer.user.repository.UserJpaRepository;
import com.example.scrumer.user.service.useCase.UserUseCase;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserJpaRepository repository;
    private final MessageMongoRepository messageRepository;
    private final UploadUseCase uploadUseCase;

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id: " + id));
    }

    @Override
    public Optional<User> findUserById(Long id) {
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
    public List<ChannelCommand> getChannels(String email) {
        User user = findByEmail(email).orElseThrow(() -> new NotFoundException("Not found user with email: " + email));
        return user
                .getUserChannels()
                .stream()
                .map(channelUser ->
                        getChannelCommand(channelUser.getChannel(), email))
                .collect(Collectors.toList());
    }

    @Override
    public List<SuggestedUserCommand> getUsers(String name) {
        return repository.findUsersByName(name).stream()
                .map(u -> SuggestedUserCommand.builder().id(u.getId()).email(u.getEmail()).username(u.getUserDetails().getUsername()).build()).collect(Collectors.toList());
    }

    @Override
    public void updateImageProfile(MultipartFile file, String email) {
        repository.findByEmail(email).ifPresent(
                user -> {
                    try {
                        UploadEntity uploadEntity = uploadUseCase.save(new SaveUploadCommand(file.getOriginalFilename(), file.getBytes(), file.getContentType()));
                        user.setImageId(uploadEntity.getId());
                        repository.save(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public ChannelCommand getChannelCommand(Channel channel, String email) {
        String channelName = "";

        ChannelUser channelUser = channel.getChannelUsers()
                .stream()
                .filter(c -> c.getUser().getEmail().equals(email))
                .findFirst()
                .orElseThrow();

        if (channel.getChannelType() == ChannelType.GROUP_CHANNEL) {
            channelName = channel.getChannelName();

        } else {
            Optional<ChannelUser> channelUserRecipient = channel.getChannelUsers()
                    .stream()
                    .filter(c -> !c.getUser().getEmail().equals(email))
                    .findFirst();
            channelName = channelUserRecipient.get().getUser().getUserDetails().getUsername();
        }

        if (channelName.equals("")) {
            channelName = "My private conversation";
        }

        ChannelCommand channelCommand =  ChannelCommand.builder()
                .idChannel(channel.getId())
                .channelName(channelName)
                .channelType(channel.getChannelType().name())
                .numberNewMessage(channelUser.getNumbersNewMessages())
                .build();

        if (channel.getIdLastMessage() != null && !channel.getIdLastMessage().equals(Strings.EMPTY)) {
            Message lastMessage = messageRepository.findById(channel.getIdLastMessage()).orElseThrow();
            channelCommand.setLastMessage(lastMessage.getContent());
        }

        return channelCommand;
    }

    @Override
    public Optional<User> findByEmail(String userEmail) {
        return repository.findByEmail(userEmail);
    }
}
