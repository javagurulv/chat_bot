package lv.javaguru.chatbot.core.services;

import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserEmailCommand;
import lv.javaguru.chatbot.core.domain.User;
import lv.javaguru.chatbot.core.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserEmailHandlerTest {

    @Mock UserRepository userRepository;

    @InjectMocks
	private RegisterUserEmailHandler handler;

    @Test
    public void shouldReturnErrorWhenTelegramIdNull() {
        RegisterUserEmailCommand command = new RegisterUserEmailCommand(null, "email@email.com");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenTelegramIdEmpty() {
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("", "email@email.com");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenNotValidatedEmailFormat_1() {
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("1", "@email.com");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "email");
        assertEquals(result.getErrors().get(0).getMessage(), "Not validated email format!");
    }

    @Test
    public void shouldReturnErrorWhenNotValidatedEmailFormat_2() {
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("1", "email");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "email");
        assertEquals(result.getErrors().get(0).getMessage(), "Not validated email format!");
    }

    @Test
    public void shouldReturnErrorWhenNotValidatedEmailFormat_3() {
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("1", "email@email");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "email");
        assertEquals(result.getErrors().get(0).getMessage(), "Not validated email format!");
    }

    @Test
    public void shouldUpdateUserEmail() {
        User user = new User();
        user.setTelegramId("111");
        user.setEmail(null);
        Mockito.when(userRepository.findByTelegramId("111")).thenReturn(Optional.of(user));
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("111", "email@email.com");
        DomainCommandResult result = handler.execute(command);
        assertFalse(result.hasErrors());
        assertEquals(user.getEmail(), "email@email.com");
    }

    @Test
    public void shouldReturnErrorNoUserWithTelegramId() {
		Mockito.when(userRepository.findByTelegramId("111")).thenReturn(Optional.empty());
		RegisterUserEmailCommand command = new RegisterUserEmailCommand("111", "email@email.com");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "User with telegram id " + "111" + " not found!");
    }
}