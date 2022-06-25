package org.juliagift.copaydrugprogram.service;

import org.juliagift.copaydrugprogram.dto.UserProfileDto;
import org.juliagift.copaydrugprogram.dto.UserRegistrationDto;
import org.juliagift.copaydrugprogram.exception.UserNotFoundException;
import org.juliagift.copaydrugprogram.model.Pharmacy;
import org.juliagift.copaydrugprogram.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	User findUserByEmail(String email);
	User registerUser(UserRegistrationDto userDto);
	User deleteUserById(UserDetails userDetails) throws UserNotFoundException;
	Pharmacy findPharmacyById(Long Id);
	User updateUser(UserProfileDto userProfileDto);
	
}
