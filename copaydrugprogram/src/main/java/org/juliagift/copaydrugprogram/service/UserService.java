package org.juliagift.copaydrugprogram.service;

import org.juliagift.copaydrugprogram.dto.UserProfileDto;
import org.juliagift.copaydrugprogram.dto.UserRegistrationDto;
import org.juliagift.copaydrugprogram.exception.UserNotFoundException;
import org.juliagift.copaydrugprogram.model.Pharmacy;
import org.juliagift.copaydrugprogram.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	// Deletes a user, given their email.
	User deleteUserByEmail(UserDetails userDetails) throws UserNotFoundException;
	
	// Returns a user, given their email.
	User findUserByEmail(String email);
	
	// Registers a user.
	User registerUser(UserRegistrationDto userDto);
	
	// Writes a user to the database.
	User saveUser(User user);
	
	// Updates a user's profile.
	User updateUser(UserProfileDto userProfileDto);	
}
