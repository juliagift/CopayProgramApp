package org.juliagift.copaydrugprogram.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.juliagift.copaydrugprogram.model.Card;
import org.juliagift.copaydrugprogram.model.Claim;
import org.juliagift.copaydrugprogram.model.Login;
import org.juliagift.copaydrugprogram.model.Pharmacy;
import org.juliagift.copaydrugprogram.model.Role;
import org.juliagift.copaydrugprogram.model.User;
import org.juliagift.copaydrugprogram.repository.ClaimRepository;
import org.juliagift.copaydrugprogram.repository.RoleRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ClaimServiceImplTest {
	
	@InjectMocks
	private ClaimServiceImpl claimServiceImpl;
	
	@Mock
	private ClaimRepository claimRepository;
	
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
		
//		 Inject into the ClaimServiceImpl test
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAllClaimsByCard() throws Exception {

		Card newCard = new Card();
		
		newCard.setBenefit(0.25);
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dob = simpleDateFormat.parse("2000-05-05");
		
		Login login = new Login("jgift@co", "jgpass");
		
		Collection<Role> roles = new ArrayList<Role>();
		Role existingRole = roleRepository.findByName("USER");
		roles.add(existingRole);
				
		User user = new User(11L, "Julia", "Gift", dob, "female", "8976564509", "5656 Happy Ln", null, "Gilbert", "AZ", "78976", "4564", login, roles);

		newCard.setUser(user);
		
		Pharmacy newPharmacy = new Pharmacy(17L, "Frys Pharmacy");
		
		Claim newClaim = Claim.builder()
							.drugCostAtClaim(500.0)
							.manufacturerPayment(450.0)
							.patientPayment(50.0)
							.status("P")
							.transactionDate(LocalDateTime.now())
							.card(newCard)
							.pharmacy(newPharmacy)
							.build();
		
		
		List<Claim> claims = new ArrayList<>();
		
		claims.add(newClaim);
		
		when(claimRepository.getAllClaimsByCard(anyLong())).thenReturn(claims);
		
		assertNotNull(claims);
		assertEquals(1, claims.size());
		
	}
	
	
	

}
