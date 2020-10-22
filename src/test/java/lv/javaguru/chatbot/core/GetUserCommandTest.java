package lv.javaguru.chatbot.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import lv.javaguru.chatbot.core.commands.GetUserCommand;
import lv.javaguru.chatbot.core.commands.GetUserResult;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class
})
public class GetUserCommandTest {

	@Autowired CommandExecutor commandExecutor;


	@Test
	@DatabaseSetup(value = "classpath:dbunit/get_user_command_setup.xml", type = DatabaseOperation.INSERT)
	@ExpectedDatabase(value = "classpath:dbunit/get_user_command_setup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "classpath:dbunit/get_user_command_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void shouldReturnErrorWhenTelegramIdIsNull() {
		GetUserCommand command = new GetUserCommand(null);
		GetUserResult result = commandExecutor.execute(command);
		assertTrue(result.hasErrors());
		assertEquals(result.getErrors().size(), 1);
		assertEquals(result.getErrors().get(0).getField(), "telegramId");
		assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
	}

	@Test
	@DatabaseSetup(value = "classpath:dbunit/get_user_command_setup.xml", type = DatabaseOperation.INSERT)
	@ExpectedDatabase(value = "classpath:dbunit/get_user_command_setup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "classpath:dbunit/get_user_command_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void shouldReturnErrorWhenTelegramIdIsEmpty() {
		GetUserCommand command = new GetUserCommand("");
		GetUserResult result = commandExecutor.execute(command);
		assertTrue(result.hasErrors());
		assertEquals(result.getErrors().size(), 1);
		assertEquals(result.getErrors().get(0).getField(), "telegramId");
		assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
	}

	@Test
	@DatabaseSetup(value = "classpath:dbunit/get_user_command_setup.xml", type = DatabaseOperation.INSERT)
	@ExpectedDatabase(value = "classpath:dbunit/get_user_command_setup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "classpath:dbunit/get_user_command_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void shouldReturnErrorWhenUserNotFoundByTelegramId() {
		GetUserCommand command = new GetUserCommand("222");
		GetUserResult result = commandExecutor.execute(command);
		assertTrue(result.hasErrors());
		assertEquals(result.getErrors().size(), 1);
		assertEquals(result.getErrors().get(0).getField(), "telegramId");
		assertEquals(result.getErrors().get(0).getMessage(), "User not found!");
	}

	@Test
	@DatabaseSetup(value = "classpath:dbunit/get_user_command_setup.xml", type = DatabaseOperation.INSERT)
	@ExpectedDatabase(value = "classpath:dbunit/get_user_command_setup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "classpath:dbunit/get_user_command_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void shouldReturnSuccessfulResult() {
		GetUserCommand command = new GetUserCommand("111");
		GetUserResult result = commandExecutor.execute(command);
		assertFalse(result.hasErrors());
		assertEquals(result.getUser().getTelegramId(), "111");
		assertFalse(result.getUser().isAdmin());
	}

}
