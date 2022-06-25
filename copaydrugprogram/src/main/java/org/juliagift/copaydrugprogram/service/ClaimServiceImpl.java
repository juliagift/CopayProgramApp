package org.juliagift.copaydrugprogram.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	
	public static final double DRUG_COST = 100.0;
	
	@Autowired
	private CardRepository cardRepository;;
	
	@Autowired
	private ClaimRepository claimRepository;
	

	@Override
	public Claim submitClaim(UserDetails userDetails, Pharmacy pharmacy) throws NotFoundException {
		String userEmail = userDetails.getUsername();	
		Card card = cardRepository.findCardByEmail(userEmail);
		Claim claim = new Claim();
		
		claim.setDrugCostAtClaim(DRUG_COST);
		claim.setManufacturerPayment((1 - card.getBenefit()) * DRUG_COST);
		claim.setPatientPayment(card.getBenefit() * DRUG_COST);
		claim.setStatus("P");
		claim.setTransactionDate(LocalDateTime.now());
		claim.setCard(card);
		claim.setPharmacy(pharmacy);

		return claimRepository.save(claim);
	}
	
	@Override
	public List<Claim> getAllClaims() throws NotFoundException {
		List<Claim> claims = new ArrayList<>();
		
		claims = claimRepository.findAll();

		return claims;
		
	}

	@Override
	public List<Claim> getAllClaimsByCard(Card card) throws NotFoundException {
		List<Claim> claims = new ArrayList<>();
		
		claims = claimRepository.getAllClaimsByCard(card.getCardId());
		
		return claims;
	}
	
	

}
