package lv.javaguru.chatbot.core.commands;

import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.errors.CoreError;
import lv.javaguru.chatbot.core.domain.User;

public class GetUserResult extends DomainCommandResult {

	private User user;

	public GetUserResult(User user) {
		this.user = user;
	}

	public GetUserResult(CoreError error) {
		super(error);
	}

	public User getUser() {
		return user;
	}

}
