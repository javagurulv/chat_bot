package lv.javaguru.chatbot.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.javaguru.chatbot.core.DomainCommandHandler;
import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserFirstNameCommand;
import lv.javaguru.chatbot.core.commands.errors.CoreError;
import lv.javaguru.chatbot.core.domain.User;
import lv.javaguru.chatbot.core.persistence.UserRepository;

@Component
public class RegisterUserFirstNameHandler implements DomainCommandHandler<RegisterUserFirstNameCommand, DomainCommandResult> {

	@Autowired private UserRepository userRepository;

	@Override
	public DomainCommandResult execute(RegisterUserFirstNameCommand command) {
		if (command.getTelegramId() == null || command.getTelegramId().isEmpty()) {
			return buildTelegramIdNotProvidedResult();
		}
		return userRepository.findByTelegramId(command.getTelegramId())
				.map(user -> updateUserFirstNameAndBuildResult(user, command))
				.orElseGet(() -> buildUserNotFoundResult(command.getTelegramId()));
	}

	private DomainCommandResult updateUserFirstNameAndBuildResult(User user,
																  RegisterUserFirstNameCommand command) {
		user.setFirstName(command.getFirstName());
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
		return RegisterUserFirstNameCommand.class;
	}

}
