package com.project.invitar.client;

import javax.ws.rs.core.Response;

public interface RegistrationClientInterface {

	public Response getSignup();

	public Response postSignup(String userName, String password,
			String firstName, String lastName, String dateOfBirth,
			String emailAddress) throws Exception;

	public Response getLogin();

	public Response postLogin(String userName, String password);

}