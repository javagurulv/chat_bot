package lv.javaguru.chatbot.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class CommandExecutorImpl implements CommandExecutor {

	private Map<Class, DomainCommandHandler> commandServiceMap;

	@Autowired(required = false)
	CommandExecutorImpl(List<DomainCommandHandler> handlers) {
		commandServiceMap = new HashMap<>();
		if (handlers != null && !handlers.isEmpty()) {
			for (DomainCommandHandler handler : handlers) {
				Class domainCommandClass = handler.getCommandType();
				commandServiceMap.put(domainCommandClass, handler);
			}
		}
	}

	@Transactional()
	public <T extends DomainCommandResult> T execute(DomainCommand<T> domainCommand) {
		DomainCommandHandler service = commandServiceMap.get(domainCommand.getClass());
		if (service != null) {
			return (T) service.execute(domainCommand);
		} else {
			throw new IllegalArgumentException(
					"Unknown domain command! " + domainCommand.toString()
			);
		}
	}

}