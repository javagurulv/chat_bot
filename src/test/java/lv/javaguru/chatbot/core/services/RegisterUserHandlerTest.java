package lv.javaguru.chatbot.core.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserCommand;
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
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class RegisterUserHandlerTest {
    private final User user = new User();
    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldReturnErrorWhenTelegramIdNullOrEmpty_1() {
        RegisterUserHandler registerUserHandler = new RegisterUserHandler();
        RegisterUserCommand command = new RegisterUserCommand("");
        DomainCommandResult result = registerUserHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenTelegramIdNullOrEmpty_2() {
        RegisterUserHandler registerUserHandler = new RegisterUserHandler();
        RegisterUserCommand command = new RegisterUserCommand(null);
        DomainCommandResult result = registerUserHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnNoErrorUserNotExists() {
        RegisterUserHandler registerUserHandler = new RegisterUserHandler();
        RegisterUserCommand command = new RegisterUserCommand("111");

        ReflectionTestUtils.setField(registerUserHandler, "userRepository", userRepository);

        DomainCommandResult result = registerUserHandler.execute(command);
        assertFalse(result.hasErrors());
    }

    @Test
    public void shouldReturnErrorUserWithTelegramIdAlreadyExist() {
        RegisterUserHandler registerUserHandler = new RegisterUserHandler();
        RegisterUserCommand command = new RegisterUserCommand("111");

        user.setTelegramId("111");
        userRepository.save(user);

        ReflectionTestUtils.setField(registerUserHandler, "userRepository", userRepository);

        DomainCommandResult result = registerUserHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "User with telegram id " + "111" + " already exist!");
        userRepository.delete(user);
    }


}