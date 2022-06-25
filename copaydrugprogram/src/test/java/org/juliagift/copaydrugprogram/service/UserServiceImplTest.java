package org.juliagift.copaydrugprogram.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.juliagift.copaydrugprogram.model.Login;
import org.juliagift.copaydrugprogram.model.Role;
import org.juliagift.copaydrugprogram.model.User;
import org.juliagift.copaydrugprogram.repository.RoleRepository;
import org.juliagift.copaydrugprogram.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

//@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	
	@InjectMocks  //this allows to inject mock objects
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
//		 Inject into the UserServiceImpl test
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindUserByEmail() throws Exception {
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date date = simpleDateFormat.parse("2000-05-05");

		Login login = new Login("jgift@co", "jgpass");

		Collection<Role> roles = new ArrayList<Role>();
		Role existingRole = roleRepository.findByName("USER");
		roles.add(existingRole);
		
		User newUser = User.builder()
						.firstName("Julia")
						.lastName("Gift")
						.dob(date)
						.address1("5656 Happy Ln")
						.address2("Apt 2")
						.city("Gilbert")
						.state("AZ")
						.zip5("78976")
						.zip4("4564")
						.login(login)
						.roles(roles)
						.build();
		
		//fake the findUserByEmail method call using mocked userRepository object
		when(userRepository.findUserByEmail(anyString())).thenReturn(newUser);
		
		User user = userServiceImpl.findUserByEmail("jgift@co");
		
		assertNotNull(user);
		assertEquals("Julia", user.getFirstName());
		
	}

}
