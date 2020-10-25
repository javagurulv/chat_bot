package lv.javaguru.chatbot.core.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserLastNameCommand;
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
public class RegisterUserLastNameHandlerTest {
    private final User user = new User();
    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldReturnErrorWhenTelegramIdNullOrEmpty_1() {
        RegisterUserLastNameHandler registerUserLastNameHandler = new RegisterUserLastNameHandler();
        RegisterUserLastNameCommand command = new RegisterUserLastNameCommand("", "User Last Name");
        DomainCommandResult result = registerUserLastNameHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenTelegramIdNullOrEmpty_2() {
        RegisterUserLastNameHandler registerUserLastNameHandler = new RegisterUserLastNameHandler();
        RegisterUserLastNameCommand command = new RegisterUserLastNameCommand(null, "User Last Name");
        DomainCommandResult result = registerUserLastNameHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldUpdateUserLastName() {
        RegisterUserLastNameHandler registerUserLastNameHandler = new RegisterUserLastNameHandler();
        RegisterUserLastNameCommand command = new RegisterUserLastNameCommand("111", "User Last Name");

        user.setTelegramId("111");
        userRepository.save(user);

        ReflectionTestUtils.setField(registerUserLastNameHandler, "userRepository", userRepository);

        DomainCommandResult result = registerUserLastNameHandler.execute(command);
        assertFalse(result.hasErrors());
        userRepository.delete(user);
    }

    @Test
    public void shouldReturnErrorNoUserWithTelegramId() {
        RegisterUserLastNameHandler registerUserLastNameHandler = new RegisterUserLastNameHandler();
        RegisterUserLastNameCommand command = new RegisterUserLastNameCommand("222", "User Last Name");

        user.setTelegramId("111");
        userRepository.save(user);

        ReflectionTestUtils.setField(registerUserLastNameHandler, "userRepository", userRepository);

        DomainCommandResult result = registerUserLastNameHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "User with telegram id " + "222" + " not found!");
        userRepository.delete(user);
    }
}