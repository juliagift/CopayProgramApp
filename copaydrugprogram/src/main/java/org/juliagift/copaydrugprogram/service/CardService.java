package org.juliagift.copaydrugprogram.service;

import java.util.List;

import org.juliagift.copaydrugprogram.model.Card;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface CardService {
	
	// Returns all the cards in the database.
	List<Card> findAllCards() throws NotFoundException;
	
	// Returns a card, given an email.
	Card findCardByEmail(String email) throws NotFoundException;
}
