package lv.javaguru.chatbot.core.services;

import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserFirstNameCommand;
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
public class RegisterUserFirstNameHandlerTest {

    @Mock UserRepository userRepository;

    @InjectMocks
    private RegisterUserFirstNameHandler handler;

    @Test
    public void shouldReturnErrorWhenTelegramIdNull() {
        RegisterUserFirstNameCommand command = new RegisterUserFirstNameCommand(null, "FirstName");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenTelegramIdEmpty() {
        RegisterUserFirstNameCommand command = new RegisterUserFirstNameCommand("", "FirstName");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldUpdateUserFirstName() {
        User user = new User();
        user.setTelegramId("11");
        user.setFirstName(null);
        Mockito.when(userRepository.findByTelegramId("11")).thenReturn(Optional.of(user));
        RegisterUserFirstNameCommand command = new RegisterUserFirstNameCommand("11", "FirstName");
        DomainCommandResult result = handler.execute(command);
        assertFalse(result.hasErrors());
        assertEquals(user.getFirstName(), "FirstName");
    }

    @Test
    public void shouldReturnErrorNoUserWithTelegramId() {
        Mockito.when(userRepository.findByTelegramId("25")).thenReturn(Optional.empty());
        RegisterUserFirstNameCommand command = new RegisterUserFirstNameCommand("25", "FirstName");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "User with telegram id " + "25" + " not found!");
    }

}