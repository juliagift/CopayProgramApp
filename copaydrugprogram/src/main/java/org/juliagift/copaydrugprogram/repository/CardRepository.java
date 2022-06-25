package org.juliagift.copaydrugprogram.repository;

import org.juliagift.copaydrugprogram.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
	
	@Query(value = "SELECT c.* FROM Card c JOIN User u ON c.user_id = u.user_id JOIN Login l ON u.login_id = l.login_id WHERE l.email = :email", nativeQuery = true)
	Card findCardByEmail(String email);

}
