package lv.javaguru.chatbot.core.services;


import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserCommand;
import lv.javaguru.chatbot.core.domain.User;
import lv.javaguru.chatbot.core.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserHandlerTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private RegisterUserHandler handler;


    @Test
    public void shouldReturnErrorWhenTelegramIdNull() {
        RegisterUserCommand command = new RegisterUserCommand(null);
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenTelegramIdEmpty() {
        RegisterUserCommand command = new RegisterUserCommand("");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldCreateNewUser() {
        Mockito.when(userRepository.findByTelegramId("25")).thenReturn(Optional.empty());
        RegisterUserCommand command = new RegisterUserCommand("25");
        DomainCommandResult result = handler.execute(command);
        assertFalse(result.hasErrors());
    }

    @Test
    public void shouldReturnErrorUserWithTelegramIdAlreadyExist() {
        User user = new User();
        user.setTelegramId("25");
        Mockito.when(userRepository.findByTelegramId("25")).thenReturn(Optional.of(user));
        RegisterUserCommand command = new RegisterUserCommand("25");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "User with telegram id 25 already exist!");
    }

}