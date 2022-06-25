package org.juliagift.copaydrugprogram.service;

import java.util.List;

import org.juliagift.copaydrugprogram.model.Card;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface CardService {
	
	Card findCardByEmail(String email) throws NotFoundException;
	List<Card> findAllCards() throws NotFoundException;
}
