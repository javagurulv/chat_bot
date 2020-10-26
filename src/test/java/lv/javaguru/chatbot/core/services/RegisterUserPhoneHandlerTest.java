package lv.javaguru.chatbot.core.services;

import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserPhoneCommand;
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


@RunWith(MockitoJUnitRunner.class)
public class RegisterUserPhoneHandlerTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private RegisterUserPhoneHandler handler;

    @Test
    public void shouldUpdateUserPhone() {
        User user = new User();
        user.setTelegramId("111");
        Mockito.when(userRepository.findByTelegramId("111")).thenReturn(Optional.of(user));
        RegisterUserPhoneCommand command = new RegisterUserPhoneCommand("111", "22222");
        DomainCommandResult result = handler.execute(command);
        assertFalse(result.hasErrors());
        assertEquals(user.getPhone(), "22222");
    }

    @Test
    public void shouldReturnErrorNotValidatedPhoneFormat_1() {
        User user = new User();
        user.setTelegramId("111");
        Mockito.when(userRepository.findByTelegramId("111")).thenReturn(Optional.of(user));
        RegisterUserPhoneCommand command = new RegisterUserPhoneCommand("111", "2sss2");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
    }

    @Test
    public void shouldReturnErrorNotValidatedPhoneFormat_2() {
        User user = new User();
        user.setTelegramId("111");
        Mockito.when(userRepository.findByTelegramId("111")).thenReturn(Optional.of(user));
        RegisterUserPhoneCommand command = new RegisterUserPhoneCommand("111", "2sss");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
    }

    @Test
    public void shouldReturnErrorNotValidatedPhoneFormat_3() {
        User user = new User();
        user.setTelegramId("111");
        Mockito.when(userRepository.findByTelegramId("111")).thenReturn(Optional.of(user));
        RegisterUserPhoneCommand command = new RegisterUserPhoneCommand("111", "sss2");
        DomainCommandResult result = handler.execute(command);
        assertTrue(result.hasErrors());
    }

}