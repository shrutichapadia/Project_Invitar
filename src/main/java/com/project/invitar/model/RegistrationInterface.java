package com.project.invitar.model;

import java.util.Date;

public interface RegistrationInterface {

	public Long getId();

	public void setId(Long id);

	public String getUserName();

	public void setUserName(String userName);

	public String getFirstName();

	public void setFirstName(String firstName);

	public String getLastName();

	public void setLastName(String lastName);

	public String getPassword();

	public void setPassword(String password);

	public String getEmailAddress();

	public void setEmailAddress(String emailAddress);

	public Date getDateOfBirth();

	public void setDateOfBirth(Date dateOfBirth);

}