package org.juliagift.copaydrugprogram.service;

import java.util.List;

import org.juliagift.copaydrugprogram.model.Card;
import org.juliagift.copaydrugprogram.model.Claim;
import org.juliagift.copaydrugprogram.model.Pharmacy;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClaimService {
	
	Claim submitClaim(UserDetails userDetails, Pharmacy pharmacy) throws NotFoundException;
	List<Claim> getAllClaims() throws NotFoundException;
	List<Claim> getAllClaimsByCard(Card card) throws NotFoundException;
}
