package lv.javaguru.chatbot.core.commands;

import lv.javaguru.chatbot.core.DomainCommand;

public class GetUserCommand implements DomainCommand<GetUserResult> {

	private String telegramId;

	public GetUserCommand(String telegramId) {
		this.telegramId = telegramId;
	}

	public String getTelegramId() {
		return telegramId;
	}

}
