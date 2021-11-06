package com.example.scrumer.user.service.useCase;

import com.example.scrumer.user.command.RegisterCommand;
import com.example.scrumer.user.entity.User;

public interface RegistrationUseCase {

    User register(RegisterCommand command);
}
