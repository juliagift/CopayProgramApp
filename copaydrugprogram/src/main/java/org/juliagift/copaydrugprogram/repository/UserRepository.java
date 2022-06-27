package org.juliagift.copaydrugprogram.repository;

import org.juliagift.copaydrugprogram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	// Returns a user, given an email.
	@Query(value = "SELECT u.* FROM User u JOIN Login l ON u.login_id = l.login_id WHERE l.email = :email",  nativeQuery = true)
	User findUserByEmail(String email);
	
}
