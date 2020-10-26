package lv.javaguru.chatbot.core.commands;

import lv.javaguru.chatbot.core.DomainCommand;
import lv.javaguru.chatbot.core.DomainCommandResult;

public class RegisterUserPhoneCommand implements DomainCommand<DomainCommandResult> {

    private final String telegramId;

    private final String phone;

    public RegisterUserPhoneCommand(String telegramId, String phone) {
        this.telegramId = telegramId;
        this.phone = phone;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public String getPhone() {
        return phone;
    }

}