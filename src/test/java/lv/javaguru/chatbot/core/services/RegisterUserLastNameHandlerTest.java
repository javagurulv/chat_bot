package lv.javaguru.chatbot.core.services;

import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserLastNameCommand;
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
public class RegisterUserLastNameHandlerTest {

    @Mock UserRepository userRepository;

    @InjectMocks
    private RegisterUserLastNameHandler handler;


    @Test
    public void shouldReturnErrorWhenTelegramIdNull() {
        RegisterUserLastNameCommand command = new RegisterUserLastNameCommand(null, "LastName");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenTelegramIdEmpty() {
        RegisterUserLastNameCommand command = new RegisterUserLastNameCommand("", "LastName");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldUpdateUserLastName() {
        User user = new User();
        user.setTelegramId("15");
        user.setLastName(null);
        Mockito.when(userRepository.findByTelegramId("15")).thenReturn(Optional.of(user));
        RegisterUserLastNameCommand command = new RegisterUserLastNameCommand("15", "LastName");
        DomainCommandResult result = handler.execute(command);
        assertFalse(result.hasErrors());
        assertEquals(user.getLastName(), "LastName");
    }


    @Test
    public void shouldReturnErrorNoUserWithTelegramId() {
        Mockito.when(userRepository.findByTelegramId("55")).thenReturn(Optional.empty());
        RegisterUserLastNameCommand command = new RegisterUserLastNameCommand("55", "LastName");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "User with telegram id 55 not found!");
    }
}