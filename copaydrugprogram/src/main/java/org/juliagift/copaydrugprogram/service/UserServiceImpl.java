package org.juliagift.copaydrugprogram.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.juliagift.copaydrugprogram.dto.UserProfileDto;
import org.juliagift.copaydrugprogram.dto.UserRegistrationDto;
import org.juliagift.copaydrugprogram.exception.UserNotFoundException;
import org.juliagift.copaydrugprogram.model.Card;
import org.juliagift.copaydrugprogram.model.Claim;
import org.juliagift.copaydrugprogram.model.Login;
import org.juliagift.copaydrugprogram.model.Pharmacy;
import org.juliagift.copaydrugprogram.model.Role;
import org.juliagift.copaydrugprogram.model.User;
import org.juliagift.copaydrugprogram.repository.CardRepository;
import org.juliagift.copaydrugprogram.repository.ClaimRepository;
import org.juliagift.copaydrugprogram.repository.LoginRepository;
import org.juliagift.copaydrugprogram.repository.PharmacyRepository;
import org.juliagift.copaydrugprogram.repository.RoleRepository;
import org.juliagift.copaydrugprogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//this class is used by spring controller to authenticate and authorize user
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	@Override
	public User registerUser(UserRegistrationDto userDto) {

		Role existingRole = roleRepository.findByName("USER");

		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setDob(userDto.getDob());
		user.setGender(userDto.getGender());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setAddress1(userDto.getAddress1());
		user.setAddress2(userDto.getAddress2());
		user.setCity(userDto.getCity());
		user.setState(userDto.getState());
		user.setZip5(userDto.getZip5());
		user.setZip4(userDto.getZip4());

		Login login = new Login(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
		user.setLogin(login);
		


		if (existingRole == null) {
			user.setRoles(Arrays.asList(new Role("USER")));
		} else {
			user.setRoles(Arrays.asList(existingRole));
		}
		
		System.out.println("I am here in the service/register");
		System.out.println(user);
		
		User newUser = userRepository.save(user);
		System.out.println(newUser);
		
		Double benefit = 0.0;
		String state = userDto.getState();
		
		if(state.equals("AL")) {
			benefit = 0.25;
		} else if(state.equals("AZ")) {
			benefit = 0.5;
		} else if(state.equals("FL")) {
			benefit = 0.75;
		}
		
		Card card = new Card(benefit, newUser);
		Card newCard = cardRepository.save(card);
		
		System.out.println(newCard);
		
		

		return newUser;
	}


	// this class is used by spring controller to authenticate and authorize user
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(email);

		System.out.println("I am here in the service/loaduserusername");
		System.out.println(user);
		
		

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getLogin().getEmail(),
				user.getLogin().getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public User deleteUserById(UserDetails userDetails) throws UserNotFoundException {
		// We don't actually want to delete a user. The user is tied to financial records
		// and we wouldn't want them to just delete their information, and create a new
		// account and get additional benefits.
		
		// Instead, we'll delete their login. They will be unable to login after this
		// and so to the user, they are effectively deleted.
		String userEmail = userDetails.getUsername();
		Card card = cardRepository.findCardByEmail(userEmail);
		User user = card.getUser();
		
		// Set the login of this user to NULL.
		// This is an UPDATE operation.
		Login login = user.getLogin();
		user.setLogin(null);
		userRepository.save(user); 
		
		// Delete the login.
		loginRepository.delete(login);
		
		return user;
	}

	@Override
	public Pharmacy findPharmacyById(Long id) {
		return pharmacyRepository.findById(id).get();
	}

	@Override
	public User updateUser(UserProfileDto userProfileDto) {
		
		Optional <User> existingUserData = userRepository.findById(userProfileDto.getUserId());

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
