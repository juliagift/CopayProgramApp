package org.juliagift.copaydrugprogram.service;

import java.util.ArrayList;
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

	@Override
	public Card findCardByEmail(String email) throws NotFoundException {
		
		Card card = cardRepository.findCardByEmail(email);
		
		System.out.println("I am here in the cardservice/findCardByEmail");
		System.out.println(card);
		
		if(card == null) {
			throw new NotFoundException();
		}
		
		return card;
	}

	@Override
	public List<Card> findAllCards() throws NotFoundException {
		List<Card> cards = new ArrayList<>();
		
		cards = cardRepository.findAll();
		
		return cards;
	}

}
