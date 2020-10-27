package lv.javaguru.chatbot.core;

public interface DomainCommandHandler<C extends DomainCommand<?>, R extends DomainCommandResult> {

	R execute(C command);

	Class<?> getCommandType();

}