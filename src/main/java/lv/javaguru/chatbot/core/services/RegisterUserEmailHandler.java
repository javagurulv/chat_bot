package lv.javaguru.chatbot.core.services;

import lv.javaguru.chatbot.core.DomainCommandHandler;
import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserEmailCommand;
import lv.javaguru.chatbot.core.commands.errors.CoreError;
import lv.javaguru.chatbot.core.domain.User;
import lv.javaguru.chatbot.core.persistence.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class RegisterUserEmailHandler implements DomainCommandHandler<RegisterUserEmailCommand, DomainCommandResult> {

    @Autowired
    public UserRepository userRepository;

    @Override
    public DomainCommandResult execute(RegisterUserEmailCommand command) {
        if (command.getTelegramId() == null || command.getTelegramId().isEmpty()) {
            return buildTelegramIdNotProvidedResult();
        }
        if (!(isValidateEmail(command))) {
            return buildEmailNotValidated();
        }

        return userRepository.findByTelegramId(command.getTelegramId())
                .map(user -> updateUserEmailAndBuildResult(user, command))
                .orElseGet(() -> buildUserNotFoundResult(command.getTelegramId()));
    }

    @Override
    public Class<RegisterUserEmailCommand> getCommandType() {
        return RegisterUserEmailCommand.class;
    }

    private boolean isValidateEmail(RegisterUserEmailCommand command) {
        return EmailValidator.getInstance().isValid(command.getEmail());
    }

    private DomainCommandResult updateUserEmailAndBuildResult(User user, RegisterUserEmailCommand command) {
        user.setEmail(command.getEmail());
        return new DomainCommandResult();
    }

    private DomainCommandResult buildUserNotFoundResult(String telegramId) {
        CoreError error = new CoreError("telegramId", "User with telegram id " + telegramId + " not found!");
        return new DomainCommandResult(error);
    }

    private DomainCommandResult buildTelegramIdNotProvidedResult() {
        CoreError error = new CoreError("telegramId", "Must be not empty!");
        return new DomainCommandResult(error);
    }

    private DomainCommandResult buildEmailNotValidated() {
        CoreError error = new CoreError("email", "Not validated email format!");
        return new DomainCommandResult(error);
    }
}