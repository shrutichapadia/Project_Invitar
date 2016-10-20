package com.project.invitar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.invitar.model.Registration;
import com.project.invitar.model.RegistrationInterface;
import com.project.invitar.repository.RegistrationRepository;

@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Transactional
	public RegistrationInterface save(RegistrationInterface registration) {
		return registrationRepository.save((Registration)registration);
	}

	public boolean findByLogin(String userName, String password) {	
		RegistrationInterface reg = registrationRepository.findByUserName(userName);
		
		if(reg != null && reg.getPassword().equals(password)) {
			return true;
		} 
		
		return false;		
	}

	public boolean findByUserName(String userName) {
		RegistrationInterface reg = registrationRepository.findByUserName(userName);
		
		if(reg != null) {
			return true;
		}
		
		return false;
	}
}
