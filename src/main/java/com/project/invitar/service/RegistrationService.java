package com.project.invitar.service;

import com.project.invitar.model.RegistrationInterface;

public interface RegistrationService {
	RegistrationInterface save(RegistrationInterface registration);
	boolean findByLogin(String userName, String password);
	boolean findByUserName(String userName);
}
