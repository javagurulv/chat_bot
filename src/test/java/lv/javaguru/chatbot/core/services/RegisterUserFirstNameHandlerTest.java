package lv.javaguru.chatbot.core.services;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import lv.javaguru.chatbot.core.DomainCommandResult;
import lv.javaguru.chatbot.core.commands.RegisterUserFirstNameCommand;
import lv.javaguru.chatbot.core.domain.User;
import lv.javaguru.chatbot.core.persistence.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class RegisterUserFirstNameHandlerTest {
    private final User user = new User();
    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldReturnErrorWhenTelegramIdNullOrEmpty_1() {
        RegisterUserFirstNameHandler registerUserFirstNameHandler = new RegisterUserFirstNameHandler();
        RegisterUserFirstNameCommand command = new RegisterUserFirstNameCommand("", "User Name");
        DomainCommandResult result = registerUserFirstNameHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldReturnErrorWhenTelegramIdNullOrEmpty_2() {
        RegisterUserFirstNameHandler registerUserFirstNameHandler = new RegisterUserFirstNameHandler();
        RegisterUserFirstNameCommand command = new RegisterUserFirstNameCommand(null, "User Name");
        DomainCommandResult result = registerUserFirstNameHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
    }

    @Test
    public void shouldUpdateUserFirstName() {
        RegisterUserFirstNameHandler registerUserFirstNameHandler = new RegisterUserFirstNameHandler();
        RegisterUserFirstNameCommand command = new RegisterUserFirstNameCommand("111", "User Name");

        user.setTelegramId("111");
        userRepository.save(user);

        ReflectionTestUtils.setField(registerUserFirstNameHandler, "userRepository", userRepository);

        DomainCommandResult result = registerUserFirstNameHandler.execute(command);
        assertFalse(result.hasErrors());
        userRepository.delete(user);
    }

    @Test
    public void shouldReturnErrorNoUserWithTelegramId() {
        RegisterUserFirstNameHandler registerUserFirstNameHandler = new RegisterUserFirstNameHandler();
        RegisterUserFirstNameCommand command = new RegisterUserFirstNameCommand("222", "User Name");

        user.setTelegramId("111");
        userRepository.save(user);

        ReflectionTestUtils.setField(registerUserFirstNameHandler, "userRepository", userRepository);

        DomainCommandResult result = registerUserFirstNameHandler.execute(command);
        assertTrue(result.hasErrors());
        assertEquals(result.getErrors().size(), 1);
        assertEquals(result.getErrors().get(0).getField(), "telegramId");
        assertEquals(result.getErrors().get(0).getMessage(), "User with telegram id " + "222" + " not found!");
        userRepository.delete(user);
    }
}