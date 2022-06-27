package org.juliagift.copaydrugprogram.repository;

import java.util.List;

import org.juliagift.copaydrugprogram.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long>{
	
	// Returns all the claims for a given card.
	@Query(value = "SELECT c.* FROM claim c WHERE c.card_id = :id",  nativeQuery = true)
	List<Claim> getAllClaimsByCard(Long id);
}
