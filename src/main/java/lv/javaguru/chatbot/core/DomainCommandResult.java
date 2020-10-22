package lv.javaguru.chatbot.core;

import java.util.ArrayList;
import java.util.List;

import lv.javaguru.chatbot.core.commands.errors.CoreError;

public class DomainCommandResult {

	private List<CoreError> errors;

	public DomainCommandResult() { }

	public DomainCommandResult(CoreError error) {
		this.errors = new ArrayList<>();
		this.errors.add(error);
	}

	public DomainCommandResult(List<CoreError> errors) {
		this.errors = errors;
	}

	public List<CoreError> getErrors() {
		return errors;
	}

	public boolean hasErrors() {
		return errors != null && errors.size() > 0;
	}
}
