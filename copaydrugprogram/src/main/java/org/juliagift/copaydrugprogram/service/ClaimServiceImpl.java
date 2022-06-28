package org.juliagift.copaydrugprogram.service;

import java.time.LocalDateTime;
import java.util.List;

import org.juliagift.copaydrugprogram.model.Card;
import org.juliagift.copaydrugprogram.model.Claim;
import org.juliagift.copaydrugprogram.model.Pharmacy;
import org.juliagift.copaydrugprogram.repository.CardRepository;
import org.juliagift.copaydrugprogram.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ClaimServiceImpl implements ClaimService {

	// The actual cost of the drug.
	// The amount the patient pays will be a percentage of this.
	public static final Double DRUG_COST = 100.0;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private ClaimRepository claimRepository;

	// Returns all the claims in the database.
	@Override
	public List<Claim> getAllClaims() throws NotFoundException {
		return claimRepository.findAll();	
	}

	// Returns all the claims for a given card.
	@Override
	public List<Claim> getAllClaimsByCard(Card card) throws NotFoundException {
		return claimRepository.getAllClaimsByCard(card.getCardId());
	}

	// Writes a claim to the database.
	@Override
	public Claim submitClaim(UserDetails userDetails, Pharmacy pharmacy) throws NotFoundException {
		Card card = cardRepository.findCardByEmail(userDetails.getUsername());
		Double patientPayment = card.getBenefit() * DRUG_COST;
		Double manufacturerPayment = DRUG_COST - patientPayment;
		
		Claim claim = new Claim("P", DRUG_COST, patientPayment, manufacturerPayment, LocalDateTime.now(), card, pharmacy);

		return claimRepository.save(claim);
	}	
}
