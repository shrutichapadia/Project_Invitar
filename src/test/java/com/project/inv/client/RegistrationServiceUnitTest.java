package com.project.inv.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.project.invitar.model.RegistrationInterface;
import com.project.invitar.repository.RegistrationRepository;
import com.project.invitar.service.RegistrationService;
import com.project.invitar.service.RegistrationServiceImpl;

public class RegistrationServiceUnitTest {
	private Mockery context;
	private RegistrationRepository registrationRepository;
	private RegistrationService registrationService;

	@Before
	public void beforeEachTest() throws Exception {
		context = new Mockery();
		registrationRepository = context.mock(RegistrationRepository.class);
		registrationService = new RegistrationServiceImpl();

		Field field = registrationService.getClass().getDeclaredField(
				"registrationRepository");
		field.setAccessible(true);
		field.set(registrationService, registrationRepository);
	}

	@Test
	public void findByLoginTest() {
		final String userName = "j2eee";
		final String password = "j2ee";
		final RegistrationInterface registrationInterface = null;

		context.checking(new Expectations() {
			{
				oneOf(registrationRepository).findByUserName(userName);
				will(returnValue(registrationInterface));
			}
		});
		registrationService.findByLogin(userName, password);
		assertNull(registrationInterface);

		final String userName1 = "j2ee";
		final RegistrationInterface registrationInterface1 = context
				.mock(RegistrationInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(registrationRepository).findByUserName(userName1);
				will(returnValue(registrationInterface1));
				oneOf(registrationInterface1).getPassword();
				will(returnValue(password));
			}
		});
		registrationService.findByLogin(userName1, password);
		assertNotNull(registrationInterface1);
		assertEquals("j2ee", password);

		final String password1 = "j2eee";

		context.checking(new Expectations() {
			{
				oneOf(registrationRepository).findByUserName(userName1);
				will(returnValue(registrationInterface1));
				oneOf(registrationInterface1).getPassword();
				will(returnValue(password1));
			}
		});
		registrationService.findByLogin(userName1, password1);
		assertNotNull(registrationInterface1);
		assertNotEquals("j2ee", password1);

		context.assertIsSatisfied();
	}

	@Test
	public void findByLoginWithNullParametersTest() {
		final String userName = null;
		final String password = null;
		registrationService = context.mock(RegistrationService.class);

		context.checking(new Expectations() {
			{
				oneOf(registrationService).findByLogin(with(aNull(String.class)),
						with(aNull(String.class)));
				will(returnValue(false));
			}
		});
		registrationService.findByLogin(userName, password);

		final String userName1 = "j2ee";

		context.checking(new Expectations() {
			{
				oneOf(registrationService).findByLogin(with(aNonNull(String.class)),
						with(aNull(String.class)));
				will(returnValue(false));
			}
		});
		registrationService.findByLogin(userName1, password);

		final String password1 = "j2eee";

		context.checking(new Expectations() {
			{
				oneOf(registrationService).findByLogin(with(aNull(String.class)),
						with(aNonNull(String.class)));
				will(returnValue(false));
			}
		});
		registrationService.findByLogin(userName, password1);

		context.checking(new Expectations() {
			{
				oneOf(registrationService).findByLogin(with(aNonNull(String.class)),
						with(aNonNull(String.class)));
				will(returnValue(false));
			}
		});
		registrationService.findByLogin(userName1, password1);

		context.assertIsSatisfied();
	}

	@Test
	public void findByEmptyLoginTest() {
		final String userName = "";
		final String password = "";
		final RegistrationInterface registrationInterface = null;

		context.checking(new Expectations() {
			{
				oneOf(registrationRepository).findByUserName(userName);
				will(returnValue(registrationInterface));
			}
		});
		registrationService.findByLogin(userName, password);
		assertNull(registrationInterface);

		context.assertIsSatisfied();
	}

	@Test
	public void findByUserNameTest() {
		final String userName = "j2ee";

		context.checking(new Expectations() {
			{
				oneOf(registrationRepository)
						.findByUserName(with(any(String.class)));
				will(returnValue(with(any(RegistrationInterface.class))));
			}
		});
		registrationService.findByUserName(userName);

		context.assertIsSatisfied();
	}

	@Test
	public void findByBadUserNameTest() {
		final String userName = "j2eee";
		final RegistrationInterface registrationInterface = null;

		context.checking(new Expectations() {
			{
				oneOf(registrationRepository)
						.findByUserName(with(any(String.class)));
				will(returnValue(registrationInterface));
			}
		});
		registrationService.findByUserName(userName);
		assertNull(registrationInterface);

		final String userName1 = "j2ee";
		final RegistrationInterface registrationInterface1 = context
				.mock(RegistrationInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(registrationRepository).findByUserName(userName1);
				will(returnValue(registrationInterface1));
			}
		});
		registrationService.findByUserName(userName1);
		assertNotNull(registrationInterface1);

		context.assertIsSatisfied();
	}
}
