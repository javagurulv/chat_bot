package lv.javaguru.chatbot.core.commands;

import lv.javaguru.chatbot.core.DomainCommand;
import lv.javaguru.chatbot.core.DomainCommandResult;

public class RegisterUserCommand implements DomainCommand<DomainCommandResult> {

	private String telegramId;

	public RegisterUserCommand(String telegramId) {
		this.telegramId = telegramId;
	}

	public String getTelegramId() {
		return telegramId;
	}
}
