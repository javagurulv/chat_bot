package lv.javaguru.chatbot.core;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import lv.javaguru.chatbot.core.commands.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})

public class UserRegistrationAcceptanceTest {

    @Autowired
    CommandExecutor commandExecutor;

    @Test
    @DatabaseSetup(value = "classpath:dbunit/user_registration_acceptance_test_setup.xml", type = DatabaseOperation.INSERT)
    @ExpectedDatabase(value = "classpath:dbunit/user_registration_acceptance_test_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(value = "classpath:dbunit/user_registration_acceptance_test_expected.xml", type = DatabaseOperation.DELETE_ALL)
    public void shouldReturnSuccessfulResult() {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand("25");
        commandExecutor.execute(registerUserCommand);

        RegisterUserFirstNameCommand registerUserFirstNameCommand = new RegisterUserFirstNameCommand("25", "John");
        commandExecutor.execute(registerUserFirstNameCommand);

        RegisterUserLastNameCommand registerUserLastNameCommand = new RegisterUserLastNameCommand("25", "Robertson");
        commandExecutor.execute(registerUserLastNameCommand);

        RegisterUserPhoneCommand registerUserPhoneCommand = new RegisterUserPhoneCommand("25", "22222222");
        commandExecutor.execute(registerUserPhoneCommand);

        RegisterUserEmailCommand registerUserEmailCommand = new RegisterUserEmailCommand("25", "bob@bob.com");
        commandExecutor.execute(registerUserEmailCommand);

    }
}
