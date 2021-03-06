package lv.javaguru.chatbot.core.services;

import lv.javaguru.chatbot.core.DomainCommandHandler;
import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserPhoneCommand;
import lv.javaguru.chatbot.core.commands.errors.CoreError;
import lv.javaguru.chatbot.core.domain.User;
import lv.javaguru.chatbot.core.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserPhoneHandler implements DomainCommandHandler<RegisterUserPhoneCommand, DomainCommandResult> {

    @Autowired
    protected UserRepository userRepository;


    @Override
    public DomainCommandResult execute(RegisterUserPhoneCommand command) {
        if (command.getTelegramId() == null || command.getTelegramId().isEmpty()) {
            return buildTelegramIdNotProvidedResult();
        }
        if (!(validatePhoneFormat(command.getPhone()))) {
            return buildNotValidatePhoneFormat();
        }
        return userRepository.findByTelegramId(command.getTelegramId())
                .map(user -> updateUserPhoneAndBuildResult(user, command))
                .orElseGet(() -> buildUserNotFoundResult(command.getTelegramId()));
    }

    @Override
    public Class<RegisterUserPhoneCommand> getCommandType() {
        return RegisterUserPhoneCommand.class;
    }

    private boolean validatePhoneFormat(String entry) {
        String regex = "^[0-9]+";
        return entry.matches(regex);
    }

    private DomainCommandResult buildNotValidatePhoneFormat() {
        CoreError error = new CoreError("phone number", "No supported phone number format");
        return new DomainCommandResult(error);
    }

    private DomainCommandResult updateUserPhoneAndBuildResult(User user, RegisterUserPhoneCommand command) {
        user.setPhone(command.getPhone());
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
}

