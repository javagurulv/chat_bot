package lv.javaguru.chatbot.core.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserEmailCommand;
import lv.javaguru.chatbot.core.domain.User;
import lv.javaguru.chatbot.core.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class RegisterUserEmailHandlerTest {
    private final User user = new User();
    @Autowired UserRepository userRepository;

    @Test
    public void shouldReturnErrorWhenTelegramIdNullOrEmpty_1() {
        RegisterUserEmailHandler registerUserEmailHandler = new RegisterUserEmailHandler();
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("", "email@email.com");
        DomainCommandResult result = registerUserEmailHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenTelegramIdNullOrEmpty_2() {
        RegisterUserEmailHandler registerUserEmailHandler = new RegisterUserEmailHandler();
        RegisterUserEmailCommand command = new RegisterUserEmailCommand(null, "email@email.com");
        DomainCommandResult result = registerUserEmailHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenNotValidatedEmailFormat_1() {
        RegisterUserEmailHandler registerUserEmailHandler = new RegisterUserEmailHandler();
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("1", "@email.com");
        DomainCommandResult result = registerUserEmailHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "email");
        assertEquals(result.getErrors().get(0).getMessage(), "Not validated email format!");
    }

    @Test
    public void shouldReturnErrorWhenNotValidatedEmailFormat_2() {
        RegisterUserEmailHandler registerUserEmailHandler = new RegisterUserEmailHandler();
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("1", "email");
        DomainCommandResult result = registerUserEmailHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "email");
        assertEquals(result.getErrors().get(0).getMessage(), "Not validated email format!");
    }

    @Test
    public void shouldReturnErrorWhenNotValidatedEmailFormat_3() {
        RegisterUserEmailHandler registerUserEmailHandler = new RegisterUserEmailHandler();
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("1", "email@email");
        DomainCommandResult result = registerUserEmailHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "email");
        assertEquals(result.getErrors().get(0).getMessage(), "Not validated email format!");
    }

    @Test
    public void shouldUpdateUserEmail() {
        RegisterUserEmailHandler registerUserEmailHandler = new RegisterUserEmailHandler();
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("111", "email@email.com");

        user.setTelegramId("111");
        userRepository.save(user);

        ReflectionTestUtils.setField(registerUserEmailHandler, "userRepository", userRepository);

        DomainCommandResult result = registerUserEmailHandler.execute(command);
        assertFalse(result.hasErrors());
        userRepository.delete(user);
    }

    @Test
    public void shouldReturnErrorNoUserWithTelegramId() {
        RegisterUserEmailHandler registerUserEmailHandler = new RegisterUserEmailHandler();
        RegisterUserEmailCommand command = new RegisterUserEmailCommand("222", "email@email.com");


        user.setTelegramId("111");
        userRepository.save(user);

        ReflectionTestUtils.setField(registerUserEmailHandler, "userRepository", userRepository);

        DomainCommandResult result = registerUserEmailHandler.execute(command);
        assertTrue(result.hasErrors());

        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "User with telegram id " + "222" + " not found!");
        userRepository.delete(user);
    }
}