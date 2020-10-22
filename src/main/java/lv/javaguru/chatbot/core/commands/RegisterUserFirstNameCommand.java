package lv.javaguru.chatbot.core.commands;

import lv.javaguru.chatbot.core.DomainCommand;
import lv.javaguru.chatbot.core.DomainCommandResult;

public class RegisterUserFirstNameCommand implements DomainCommand<DomainCommandResult> {

	private String telegramId;

	private String firstName;

	public RegisterUserFirstNameCommand(String telegramId, String firstName) {
		this.telegramId = telegramId;
		this.firstName = firstName;
	}

	public String getTelegramId() {
		return telegramId;
	}

	public String getFirstName() {
		return firstName;
	}
}
