package org.juliagift.copaydrugprogram.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.juliagift.copaydrugprogram.model.Card;
import org.juliagift.copaydrugprogram.model.Login;
import org.juliagift.copaydrugprogram.model.Role;
import org.juliagift.copaydrugprogram.model.User;
import org.juliagift.copaydrugprogram.repository.CardRepository;
import org.juliagift.copaydrugprogram.repository.RoleRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CardServiceImplTest {
	
	@InjectMocks
	private CardServiceImpl cardServiceImpl;
	
	@Mock
	private CardRepository cardRepository;
	
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
		
//		 Inject into the CardServiceImpl test
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindCardByEmail() throws Exception {
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dob = simpleDateFormat.parse("2000-05-05");
		
		Login login = new Login("jgift@co", "jgpass");
		
		Collection<Role> roles = new ArrayList<Role>();
		Role existingRole = roleRepository.findByName("USER");
		roles.add(existingRole);
		
		User user = new User(11L, "Julia", "Gift", dob, "female", "8976564509", "5656 Happy Ln", null, "Gilbert", "AZ", "78976", "4564", login, roles);
		
		Card newCard = Card.builder()
						.benefit(0.25)
						.user(user)
						.build();
		
		when(cardRepository.findCardByEmail(anyString())).thenReturn(newCard);
		
		assertNotNull(newCard);
		assertEquals("jgift@co", newCard.getUser().getLogin().getEmail());

	}

}
