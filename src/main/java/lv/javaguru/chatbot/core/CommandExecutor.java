package lv.javaguru.chatbot.core;

public interface CommandExecutor {

	<T extends DomainCommandResult> T execute(DomainCommand<T> domainCommand);

}