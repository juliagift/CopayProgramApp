package org.juliagift.copaydrugprogram.service;

import java.util.List;

import org.juliagift.copaydrugprogram.model.Card;
import org.juliagift.copaydrugprogram.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService{
	
	@Autowired
	private CardRepository cardRepository;

	// Returns all the cards in the database.
	@Override
	public List<Card> findAllCards() throws NotFoundException {
		return cardRepository.findAll();
	}

	// Returns a card, given an email.
	@Override
	public Card findCardByEmail(String email) throws NotFoundException {
		
		Card card = cardRepository.findCardByEmail(email);
		
		if(card == null) {
			throw new NotFoundException();
		}
		
		return card;
	}
}
