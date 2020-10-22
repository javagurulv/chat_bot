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

import lv.javaguru.chatbot.core.commands.RegisterUserCommand;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class
})
public class RegisterUserCommandTest {

	@Autowired CommandExecutor commandExecutor;

	@Test
	@DatabaseSetup(value = "classpath:dbunit/register_user_command_setup.xml", type = DatabaseOperation.INSERT)
	@ExpectedDatabase(value = "classpath:dbunit/register_user_command_setup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "classpath:dbunit/register_user_command_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void shouldReturnErrorWhenTelegramIdIsNull() {
		RegisterUserCommand command = new RegisterUserCommand(null);
		DomainCommandResult result = commandExecutor.execute(command);
		assertTrue(result.hasErrors());
		assertEquals(result.getErrors().size(), 1);
		assertEquals(result.getErrors().get(0).getField(), "telegramId");
		assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
	}

	@Test
	@DatabaseSetup(value = "classpath:dbunit/register_user_command_setup.xml", type = DatabaseOperation.INSERT)
	@ExpectedDatabase(value = "classpath:dbunit/register_user_command_setup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "classpath:dbunit/register_user_command_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void shouldReturnErrorWhenTelegramIdIsEmpty() {
		RegisterUserCommand command = new RegisterUserCommand("");
		DomainCommandResult result = commandExecutor.execute(command);
		assertTrue(result.hasErrors());
		assertEquals(result.getErrors().size(), 1);
		assertEquals(result.getErrors().get(0).getField(), "telegramId");
		assertEquals(result.getErrors().get(0).getMessage(), "Must be not empty!");
	}

	@Test
	@DatabaseSetup(value = "classpath:dbunit/register_user_command_setup.xml", type = DatabaseOperation.INSERT)
	@ExpectedDatabase(value = "classpath:dbunit/register_user_command_setup.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "classpath:dbunit/register_user_command_setup.xml", type = DatabaseOperation.DELETE_ALL)
	public void shouldReturnErrorWhenUserWithTelegramIdAlreadyExist() {
		RegisterUserCommand command = new RegisterUserCommand("111");
		DomainCommandResult result = commandExecutor.execute(command);
		assertTrue(result.hasErrors());
		assertEquals(result.getErrors().size(), 1);
		assertEquals(result.getErrors().get(0).getField(), "telegramId");
		assertEquals(result.getErrors().get(0).getMessage(), "User with telegram id 111 already exist!");
	}

	@Test
	@DatabaseSetup(value = "classpath:dbunit/register_user_command_setup.xml", type = DatabaseOperation.INSERT)
	@ExpectedDatabase(value = "classpath:dbunit/register_user_command_expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@DatabaseTearDown(value = "classpath:dbunit/register_user_command_expected.xml", type = DatabaseOperation.DELETE_ALL)
	public void shouldReturnSuccessfulResult() {
		RegisterUserCommand command = new RegisterUserCommand("222");
		DomainCommandResult result = commandExecutor.execute(command);
		assertFalse(result.hasErrors());
	}
}
