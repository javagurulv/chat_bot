package lv.javaguru.chatbot.core.commands;

import lv.javaguru.chatbot.core.DomainCommand;
import lv.javaguru.chatbot.core.DomainCommandResult;

public class RegisterUserEmailCommand implements DomainCommand<DomainCommandResult> {

    private final String telegramId;

    private final String email;

    public RegisterUserEmailCommand(String telegramId, String email) {
        this.telegramId = telegramId;
        this.email = email;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public String getEmail() {
        return email;
    }
}