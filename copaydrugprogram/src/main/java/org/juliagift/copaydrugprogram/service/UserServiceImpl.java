package org.juliagift.copaydrugprogram.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.juliagift.copaydrugprogram.dto.UserProfileDto;
import org.juliagift.copaydrugprogram.dto.UserRegistrationDto;
import org.juliagift.copaydrugprogram.exception.UserNotFoundException;
import org.juliagift.copaydrugprogram.model.Card;
import org.juliagift.copaydrugprogram.model.Login;
import org.juliagift.copaydrugprogram.model.Role;
import org.juliagift.copaydrugprogram.model.User;
import org.juliagift.copaydrugprogram.repository.CardRepository;
import org.juliagift.copaydrugprogram.repository.LoginRepository;
import org.juliagift.copaydrugprogram.repository.RoleRepository;
import org.juliagift.copaydrugprogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// This class is used by Spring Controller to authenticate and authorize the user.
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;

	// Deletes a user, given their email.
	
	/*******************************************
	 * THIS IS AN UPDATE AND DELETE OPERATION. *
	 *******************************************/
	@Override
	public User deleteUserByEmail(UserDetails userDetails) throws UserNotFoundException {
		// We don't actually want to delete a user. The user is tied to financial records
		// and we wouldn't want them to just delete their information, and create a new
		// account and get additional benefits.
		
		// Instead, we'll delete their login. They will be unable to login after this
		// and so to the user, they are effectively deleted.
		User user = cardRepository.findCardByEmail(userDetails.getUsername()).getUser();
		
		// Set the login of this user to NULL.
		// This is an UPDATE operation.
		Login login = user.getLogin();
		user.setLogin(null);
		userRepository.save(user); 
		
		// Delete the login.
		loginRepository.delete(login);
		
		return user;
	}

	// Returns a user, given their email.
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	// Registers a user.
	
	/********************************
	 * THIS IS AN INSERT OPERATION. *
	 ********************************/
	@Override
	public User registerUser(UserRegistrationDto userDto) {

		Collection<Role> roles = new ArrayList<Role>();
		Role role = roleRepository.findByName("USER");

		if (role == null) {
			roles.add(new Role("USER"));
		} else {
			roles.add(role);
		}
		
		Login login = new Login(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
		
		User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getDob(), userDto.getGender(), userDto.getPhoneNumber(), userDto.getAddress1(), userDto.getAddress2(), userDto.getCity(), userDto.getState(), userDto.getZip5(), userDto.getZip4(), login, roles);
		
		// This user will have the user ID that was auto-generated from the database.
		User newUser = userRepository.save(user);
		
		// Next, calculate the patient benefit.
		// The benefit varies depending on the patient's state.
		Double benefit = 0.0;
		String state = userDto.getState();
		
		if(state.equals("AL")) {
			benefit = 0.25;
		} else if(state.equals("AZ")) {
			benefit = 0.5;
		} else if(state.equals("FL")) {
			benefit = 0.75;
		}
		
		cardRepository.save(new Card(benefit, newUser));
		
		return newUser;
	}

	// This class is used by Spring Controller to authenticate and authorize the user.
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getLogin().getEmail(),
				user.getLogin().getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	// Writes a user to the database.
	@Override
	public User saveUser(User user) {
		User newUser = userRepository.save(user);
		
		return newUser;
	}

	// Updates a user's profile.
	
	/********************************
	 * THIS IS AN UPDATE OPERATION. *
	 ********************************/
	@Override
	public User updateUser(UserProfileDto userProfileDto) {
		
		// Lookup the user by user ID.
		Optional <User> existingUserData = userRepository.findById(userProfileDto.getUserId());

		// If found, set the user's attributes to the data submitted on the Edit Profile form.
		if (existingUserData.isPresent()) {
			User existingUser = existingUserData.get();
			existingUser.setFirstName(userProfileDto.getFirstName());
			existingUser.setLastName(userProfileDto.getLastName());
			existingUser.setDob(userProfileDto.getDob());
			existingUser.setGender(userProfileDto.getGender());
			existingUser.setPhoneNumber(userProfileDto.getPhoneNumber());
			existingUser.setAddress1(userProfileDto.getAddress1());
			existingUser.setAddress2(userProfileDto.getAddress2());
			existingUser.setCity(userProfileDto.getCity());
			existingUser.setState(userProfileDto.getState());
			existingUser.setZip5(userProfileDto.getZip5());
			existingUser.setZip4(userProfileDto.getZip4());

			userRepository.save(existingUser);
			
			return existingUser;
		}

		return null;
	}
}
