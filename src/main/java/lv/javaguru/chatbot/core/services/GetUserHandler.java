package lv.javaguru.chatbot.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.javaguru.chatbot.core.DomainCommandHandler;
import lv.javaguru.chatbot.core.commands.GetUserCommand;
import lv.javaguru.chatbot.core.commands.GetUserResult;
import lv.javaguru.chatbot.core.commands.errors.CoreError;
import lv.javaguru.chatbot.core.persistence.UserRepository;

@Component
public class GetUserHandler implements DomainCommandHandler<GetUserCommand, GetUserResult> {

	@Autowired private UserRepository userRepository;

	@Override
	public GetUserResult execute(GetUserCommand command) {
		if (command.getTelegramId() == null || command.getTelegramId().isEmpty()) {
			CoreError error = new CoreError("telegramId", "Must be not empty!");
			return new GetUserResult(error);
		}

		return userRepository.findByTelegramId(command.getTelegramId())
				.map(GetUserResult::new)
				.orElseGet(() -> new GetUserResult(
							new CoreError("telegramId", "User not found!")
					)
				);
	}

	@Override
	public Class getCommandType() {
		return GetUserCommand.class;
	}

}
