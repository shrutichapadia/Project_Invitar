package com.project.inv.client;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.text.ParseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.mvc.Viewable;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.project.invitar.model.RegistrationInterface;
import com.project.invitar.resource.RegistrationResource;
import com.project.invitar.resource.RegistrationResourceInterface;
import com.project.invitar.service.RegistrationService;

public class RegistrationResourceUnitTest {
	private Mockery context;
	private RegistrationService registrationService;
	private RegistrationResourceInterface registrationResourceInterface;

	@Before
	public void beforeEachTest() throws Exception {
		context = new Mockery();
		registrationService = context.mock(RegistrationService.class);
		registrationResourceInterface = new RegistrationResource();

		Field field = registrationResourceInterface.getClass().getDeclaredField(
				"registrationService");
		field.setAccessible(true);
		field.set(registrationResourceInterface, registrationService);
	}

	@Test
	public void postSignupResourceTest() throws ParseException {
		final String userName = "jersey";
		String password = "jersey";
		String firstName = "jersey";
		String lastName = "jersey";
		String dateOfBirth = "12/21/2013";
		String emailAddress = "jersey@gmail.com";
		final RegistrationInterface reg = context.mock(RegistrationInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(registrationService).findByUserName(userName);
				will(returnValue(false));
				oneOf(registrationService).save(with(any(RegistrationInterface.class)));
				will(returnValue(reg));
			}
		});
		registrationResourceInterface.signup(userName, password, firstName,
				lastName, dateOfBirth, emailAddress);

		context.assertIsSatisfied();
	}

	@Test
	public void postSignupResourceForExistingUserTest() throws ParseException {
		final String userName = "jersey";
		String password = "jersey";
		String firstName = "jersey";
		String lastName = "jersey";
		String dateOfBirth = "12/21/2013";
		String emailAddress = "jersey@gmail.com";

		context.checking(new Expectations() {
			{
				oneOf(registrationService).findByUserName(userName);
				will(returnValue(true));
			}
		});
		registrationResourceInterface.signup(userName, password, firstName,
				lastName, dateOfBirth, emailAddress);

		context.assertIsSatisfied();
	}

	@Test
	public void postSignupResourceForBadRequestTest() throws ParseException {
		String userName = null;
		String password = null;
		String firstName = null;
		String lastName = null;
		String dateOfBirth = null;
		String emailAddress = null;
		final RegistrationResourceInterface registrationResourceInterface = context
				.mock(RegistrationResourceInterface.class);
		final Response response = Response.status(Status.PRECONDITION_FAILED)
				.build();
		final Response response1 = Response.ok().entity(new Viewable("/login"))
				.build();

		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface).signup(
						with(aNull(String.class)), with(aNull(String.class)),
						with(aNull(String.class)), with(aNull(String.class)),
						with(aNull(String.class)), with(aNull(String.class)));
				will(returnValue(response));
			}
		});
		registrationResourceInterface.signup(userName, password, firstName,
				lastName, dateOfBirth, emailAddress);
		assertEquals(response.getStatus(),
				Status.PRECONDITION_FAILED.getStatusCode());

		userName = "jersey";
		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface).signup(
						with(aNonNull(String.class)),
						with(aNull(String.class)), with(aNull(String.class)),
						with(aNull(String.class)), with(aNull(String.class)),
						with(aNull(String.class)));
				will(returnValue(response));
			}
		});
		registrationResourceInterface.signup(userName, password, firstName,
				lastName, dateOfBirth, emailAddress);
		assertEquals(response.getStatus(),
				Status.PRECONDITION_FAILED.getStatusCode());

		password = "jersey";
		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface).signup(
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNull(String.class)), with(aNull(String.class)),
						with(aNull(String.class)), with(aNull(String.class)));
				will(returnValue(response));
			}
		});
		registrationResourceInterface.signup(userName, password, firstName,
				lastName, dateOfBirth, emailAddress);
		assertEquals(response.getStatus(),
				Status.PRECONDITION_FAILED.getStatusCode());

		firstName = "jersey";
		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface).signup(
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNull(String.class)), with(aNull(String.class)),
						with(aNull(String.class)));
				will(returnValue(response));
			}
		});
		registrationResourceInterface.signup(userName, password, firstName,
				lastName, dateOfBirth, emailAddress);

		lastName = "jersey";
		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface).signup(
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNull(String.class)), with(aNull(String.class)));
				will(returnValue(response));
			}
		});
		registrationResourceInterface.signup(userName, password, firstName,
				lastName, dateOfBirth, emailAddress);
		assertEquals(response.getStatus(),
				Status.PRECONDITION_FAILED.getStatusCode());

		dateOfBirth = "12/20/2013";
		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface)
						.signup(with(aNonNull(String.class)),
								with(aNonNull(String.class)),
								with(aNonNull(String.class)),
								with(aNonNull(String.class)),
								with(aNonNull(String.class)),
								with(aNull(String.class)));
				will(returnValue(response));
			}
		});
		registrationResourceInterface.signup(userName, password, firstName,
				lastName, dateOfBirth, emailAddress);
		assertEquals(response.getStatus(),
				Status.PRECONDITION_FAILED.getStatusCode());

		emailAddress = "jersey@gmail.com";
		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface).signup(
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)));
				will(returnValue(response1));
			}
		});
		registrationResourceInterface.signup(userName, password, firstName,
				lastName, dateOfBirth, emailAddress);
		assertEquals(response1.getStatus(), Status.OK.getStatusCode());

		context.assertIsSatisfied();
	}

	@Test
	public void postLoginResourceTest() throws ParseException {
		final String userName = "jersey";
		final String password = "jersey";

		context.checking(new Expectations() {
			{
				oneOf(registrationService).findByLogin(userName, password);
				will(returnValue(true));
			}
		});
		registrationResourceInterface.login(userName, password);

		final String password1 = "jersey123";
		context.checking(new Expectations() {
			{
				oneOf(registrationService).findByLogin(userName, password1);
				will(returnValue(false));
			}
		});
		registrationResourceInterface.login(userName, password1);

		context.assertIsSatisfied();
	}

	@Test
	public void postLoginResourceForBadRequestTest() throws ParseException {
		final String userName = null;
		final String password = null;
		final RegistrationResourceInterface registrationResourceInterface = context
				.mock(RegistrationResourceInterface.class);
		final Response response = Response.status(Status.PRECONDITION_FAILED)
				.build();
		final Response response1 = Response.ok().entity(new Viewable("/login"))
				.build();

		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface).login(
						with(aNull(String.class)), with(aNull(String.class)));
				will(returnValue(response));
			}
		});
		registrationResourceInterface.login(userName, password);
		assertEquals(response.getStatus(),
				Status.PRECONDITION_FAILED.getStatusCode());

		final String userName1 = "jersey";

		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface)
						.login(with(aNonNull(String.class)),
								with(aNull(String.class)));
				will(returnValue(response));
			}
		});
		registrationResourceInterface.login(userName1, password);
		assertEquals(response.getStatus(),
				Status.PRECONDITION_FAILED.getStatusCode());

		final String password1 = "jersey";
		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface)
						.login(with(aNull(String.class)),
								with(aNonNull(String.class)));
				will(returnValue(response));
			}
		});
		registrationResourceInterface.login(userName, password1);
		assertEquals(response.getStatus(),
				Status.PRECONDITION_FAILED.getStatusCode());

		context.checking(new Expectations() {
			{
				oneOf(registrationResourceInterface).login(
						with(aNonNull(String.class)),
						with(aNonNull(String.class)));
				will(returnValue(response1));
			}
		});
		registrationResourceInterface.login(userName1, password1);
		assertEquals(response1.getStatus(), Status.OK.getStatusCode());

		context.assertIsSatisfied();
	}
}
