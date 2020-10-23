package lv.javaguru.chatbot.core.services;

import lv.javaguru.chatbot.core.DomainCommandHandler;
import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserLastNameCommand;
import lv.javaguru.chatbot.core.commands.errors.CoreError;
import lv.javaguru.chatbot.core.domain.User;
import lv.javaguru.chatbot.core.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserLastNameHandler implements DomainCommandHandler<RegisterUserLastNameCommand, DomainCommandResult> {

    @Autowired private UserRepository userRepository;

    @Override
    public DomainCommandResult execute(RegisterUserLastNameCommand command) {
        if (command.getTelegramId() == null || command.getTelegramId().isEmpty()) {
            return buildTelegramIdNotProvidedResult();
        }
        return userRepository.findByTelegramId(command.getTelegramId())
                .map(user -> updateUserLastNameAndBuildResult(user, command))
                .orElseGet(() -> buildUserNotFoundResult(command.getTelegramId()));
    }

    private DomainCommandResult updateUserLastNameAndBuildResult(User user, RegisterUserLastNameCommand command) {
        user.setLastName(command.getLastName());
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


    @Override
    public Class getCommandType() {
        return RegisterUserLastNameCommand.class;
    }
}
