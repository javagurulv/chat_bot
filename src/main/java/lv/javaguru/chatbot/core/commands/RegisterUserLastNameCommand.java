package lv.javaguru.chatbot.core.commands;

import lv.javaguru.chatbot.core.DomainCommand;
import lv.javaguru.chatbot.core.DomainCommandResult;

public class RegisterUserLastNameCommand implements DomainCommand<DomainCommandResult> {

    private String telegramId;

    private String lastName;

    public RegisterUserLastNameCommand (String telegramId, String lastName) {
        this.telegramId = telegramId;
        this.lastName = lastName;
    }

    public String getTelegramId(){
        return telegramId;
    }

    public String getLastName() {
        return lastName;
    }

}