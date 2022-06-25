package org.juliagift.copaydrugprogram.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.juliagift.copaydrugprogram.model.Claim;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClaimRepositoryTest {
	
	@Autowired
	private ClaimRepository claimRepository;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAllClaimsByCard() {
		List<Claim> claimsList = claimRepository.getAllClaimsByCard(5L);
		
		claimsList.forEach(System.out::println);
		
		assertNotNull(claimsList);
		assertEquals(2, claimsList.size());
	}

}
