package com.project.inv.client;

import javax.ws.rs.client.ClientBuilder;

import org.junit.Before;
import org.junit.Test;

import com.project.invitar.client.RegistrationClient;


public class RegistrationClientIntegrationTest {
	private RegistrationClient client;	
		
	@Before
	public void beforeEachTest() {
		this.client = new RegistrationClient(ClientBuilder.newClient().target("http://localhost:8080/Invitar/webapi/registrationResourceInterface/"));
	}
		
	@Test(expected=RuntimeException.class)
	public void PostSignupFailureForExistingUserTest() throws Exception {
		client.postSignup("j2ee", "j2ee", "jersey", "jersey", "12/15/2013", "jersey@gmail.com");	
	}
	
	@Test(expected=Exception.class)
	public void PostSignupInvalidDateTest() throws Exception {
		client.postSignup("jersey", "jersey", "jersey", "jersey", "12-15-2013", "jersey@gmail.com");
	}
	
	@Test(expected=RuntimeException.class)
	public void PostSignupBadRequestTest() throws Exception {
		client.postSignup(null, null, null, null, null, null);
	}
		
	@Test(expected=RuntimeException.class)
	public void PostLoginFailureTest() {
		client.postLogin("jersey", "jersey123");
	}
	
	@Test(expected=RuntimeException.class)
	public void PostLoginBadRequestTest() {
		client.postLogin(null, null);
	}

}
