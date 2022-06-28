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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminDashboardController {

	@Autowired
	private ClaimService claimService;
	
	@Autowired
	private CardService cardService;

	@Autowired
	private UserService userService;
	
	// Returns the Admin Dashboard.
	@GetMapping("/adminDashboard")
	public ModelAndView showAdminDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		User user = userService.findUserByEmail(userDetails.getUsername());
		model.addAttribute("user", user);

		return new ModelAndView("adminDashboard");
	}

	// Returns all claims in the database.
	@GetMapping("/allClaims")
	public String getAllClaims(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		
		User user = userService.findUserByEmail(userDetails.getUsername());
		model.addAttribute("user", user);
		
		try {
			List<Claim> claims = claimService.getAllClaims();
			model.addAttribute("claims", claims);
			
			return "allClaims";
		} catch (NotFoundException e) {
			System.out.println("No Claims found!");
			return "redirect:/adminDashboard";
		}
	}
	
	// Returns all the patients in the database.
	@GetMapping("/allPatients")
	public String getAllCards(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		
		User user = userService.findUserByEmail(userDetails.getUsername());
		model.addAttribute("user", user);
		
		try {
			List<Card> cards = cardService.findAllCards();
			model.addAttribute("cards", cards);
			
			return "allUsers";
		} catch (NotFoundException e) {
			System.out.println("No Cards found!");	
			return "redirect:/adminDashboard";
		}
	}
}
