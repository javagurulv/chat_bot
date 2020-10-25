package lv.javaguru.chatbot.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lv.javaguru.chatbot.core.DomainCommandHandler;
import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserCommand;
import lv.javaguru.chatbot.core.commands.errors.CoreError;
import lv.javaguru.chatbot.core.domain.User;
import lv.javaguru.chatbot.core.persistence.UserRepository;

@Component
public class RegisterUserHandler implements DomainCommandHandler<RegisterUserCommand, DomainCommandResult> {

	@Autowired protected UserRepository userRepository;

	@Override
	public DomainCommandResult execute(RegisterUserCommand command) {
		if (command.getTelegramId() == null || command.getTelegramId().isEmpty()) {
			CoreError error = new CoreError("telegramId", "Must be not empty!");
			return new DomainCommandResult(error);
		}
		return userRepository.findByTelegramId(command.getTelegramId())
				.map(user -> buildUserAlreadyExistResult(command))
				.orElseGet(() -> buildUserNotExistResult(command));
	}

	private DomainCommandResult buildUserNotExistResult(RegisterUserCommand command) {
		User user = new User();
		user.setTelegramId(command.getTelegramId());
		userRepository.save(user);
		return new DomainCommandResult();
	}

	private DomainCommandResult buildUserAlreadyExistResult(RegisterUserCommand command) {
		String errorMessage = "User with telegram id " + command.getTelegramId() + " already exist!";
		CoreError error = new CoreError("telegramId", errorMessage);
		return new DomainCommandResult(error);
	}

	@Override
	public Class<RegisterUserCommand> getCommandType() {
		return RegisterUserCommand.class;
	}

}
