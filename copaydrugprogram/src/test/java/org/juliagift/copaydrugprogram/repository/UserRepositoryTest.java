package org.juliagift.copaydrugprogram.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.juliagift.copaydrugprogram.model.Role;
import org.juliagift.copaydrugprogram.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // test executed against database
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;

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
	void testFindUserByEmail() {
		User user1 = userRepository.findUserByEmail("forgetful.jones@aol.com");

		// should test with a deleted user
		User user2 = userRepository.findUserByEmail("notinthedb@gmail.com");

		assertNotNull(user1);
		assertNull(user2);

		assertEquals("Jannike", user1.getFirstName());

		User user = userRepository.findUserByEmail("forgetful.jones@aol.com");

		Collection<Role> roles = user.getRoles();

		String roleName = null;

		for (Role role : roles) {

			roleName = role.getName();
		}

		System.out.println(roleName);

		assertEquals("USER", roleName);
	}

}
