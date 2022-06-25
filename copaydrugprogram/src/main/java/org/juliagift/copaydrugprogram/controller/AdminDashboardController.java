package org.juliagift.copaydrugprogram.controller;

import java.util.List;

import org.juliagift.copaydrugprogram.model.Card;
import org.juliagift.copaydrugprogram.model.Claim;
import org.juliagift.copaydrugprogram.model.User;
import org.juliagift.copaydrugprogram.service.CardService;
import org.juliagift.copaydrugprogram.service.ClaimService;
import org.juliagift.copaydrugprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminDashboardController {

	@Autowired
	private ClaimService claimService;
	
	@Autowired
	private CardService cardService;

	@GetMapping("/adminDashboard")
	public ModelAndView showAdminDashboard() {
		return new ModelAndView("adminDashboard");
	}

	@GetMapping("/allClaims")
	public String getAllClaims(Model model) {
		try {
			List<Claim> claims = claimService.getAllClaims();
			model.addAttribute("claims", claims);
			
			return "allClaims";
		} catch (NotFoundException e) {
			System.out.println("No Claims found");
			
			return "redirect:/adminDashboard";
		}
	}
	
	@GetMapping("/allPatients")
	public String getAllCards(Model model) {
		try {
			List<Card> cards = cardService.findAllCards();
			model.addAttribute("cards", cards);
			
			return "allUsers";
		} catch (NotFoundException e) {
			System.out.println("No Cards found");
			
			return "redirect:/adminDashboard";
		}
	}

}
