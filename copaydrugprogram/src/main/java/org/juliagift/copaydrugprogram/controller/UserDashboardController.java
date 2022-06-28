package org.juliagift.copaydrugprogram.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.juliagift.copaydrugprogram.dto.ClaimSubmissionDto;
import org.juliagift.copaydrugprogram.dto.UserProfileDto;
import org.juliagift.copaydrugprogram.exception.UserNotFoundException;
import org.juliagift.copaydrugprogram.model.Card;
import org.juliagift.copaydrugprogram.model.Claim;
import org.juliagift.copaydrugprogram.model.Pharmacy;
import org.juliagift.copaydrugprogram.model.User;
import org.juliagift.copaydrugprogram.service.CardService;
import org.juliagift.copaydrugprogram.service.ClaimService;
import org.juliagift.copaydrugprogram.service.PharmacyService;
import org.juliagift.copaydrugprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserDashboardController {

	@Autowired
	private CardService cardService;

	@Autowired
	private ClaimService claimService;

	@Autowired
	private UserService userService;

	@Autowired
	private PharmacyService pharmacyService;
	
	// Returns the User Dashboard.
	@GetMapping("/userDashboard")
	public String showUserDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model,
			RedirectAttributes redirectAttributes) {
		
		Card card = null;
		
		// Lookup the user's card by their email.
		try {
			card = cardService.findCardByEmail(userDetails.getUsername());
			model.addAttribute("card", card);

		} catch (NotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "No cards found!");
		}
		
		// Check to see if there are any pharmacies in the database.
		// If not, add some.
		List<Pharmacy> pharmacies = null;
		
		try {
			pharmacies = pharmacyService.getAllPharmacies();
		} catch (NotFoundException e) {}

		if (pharmacies.isEmpty()) {
			initializePharmacies();
		}
		
		return "userDashboard";
	}
	

	// If there are no pharmacies in the database, populate some.
	// Our specialty drug is only available at these 15 pharmacies.
	
	/********************************
	 * THIS IS AN INSERT OPERATION. *
	 ********************************/
	public List<Pharmacy> initializePharmacies() {
		List<Pharmacy> thePharmacies = new ArrayList<Pharmacy>();
		
		thePharmacies.add(new Pharmacy("CVS 3272", "4805075399", "2371 E Guadalupe Rd", null, "Gilbert", "AZ",
				"85234", null));
		thePharmacies.add(
				new Pharmacy("CVS 9222", "6022725601", "4275 W Thomas Rd", null, "Phoenix", "AZ", "85019", null));
		thePharmacies.add(new Pharmacy("Walmart 5186", "4802246900", "2501 S Market St", null, "Gilbert", "AZ",
				"85295", null));
		thePharmacies.add(new Pharmacy("Walmart 3789", "6022757507", "3605 E Thomas Rd", null, "Phoenix", "AZ",
				"85018", null));
		thePharmacies.add(new Pharmacy("Frys 628", "4807067340", "3949 E Chandler Blvd", null, "Phoenix", "AZ",
				"85048", null));

		thePharmacies.add(
				new Pharmacy("CVS 1822", "2519478602", "21975 Hwy 59", null, "Robertsdale", "AL", "36567", null));
		thePharmacies.add(
				new Pharmacy("CVS 10516", "2568515350", "5859 Highway 53", null, "Harvest", "AL", "35749", null));
		thePharmacies.add(
				new Pharmacy("Walmart 1201", "2056318110", "890 Odum Rd", null, "Gardendale", "AL", "35071", null));
		thePharmacies.add(new Pharmacy("Walmart 7280", "2566156667", "5601 Al Highway 157", null, "Cullman", "AL",
				"35058", null));
		thePharmacies.add(new Pharmacy("Birmingham Loyalty Kroger", "8005764377", "14 W Oxmoor Rd", null,
				"Homewood", "AL", "35209", null));

		thePharmacies.add(
				new Pharmacy("CVS 613", "8139351134", "9202 N Florida Ave", null, "Tampa", "FL", "33612", null));
		thePharmacies.add(new Pharmacy("CVS 5400", "4072384726", "6790 Central Florida Pkwy", null, "Orlando", "FL",
				"32821", null));
		thePharmacies.add(new Pharmacy("Walmart 5871", "3212474817", "5734 S Orange Blossom Trl", null, "Orlando",
				"FL", "32839", null));
		thePharmacies.add(new Pharmacy("Walmart 2727", "3052424447", "33501 S Dixie Hwy", null, "Florida City",
				"FL", "33034", null));
		thePharmacies.add(new Pharmacy("Groveland Loyalty", "8005764377", "7925 American Way", null, "Groveland",
				"FL", "34736", null));

		for (Pharmacy pharmacy : thePharmacies) {
			pharmacyService.savePharmacy(pharmacy);
		}
		
		return thePharmacies;
	}

	// Returns the page for the user to submit claims.
	@GetMapping("/claim")
	public String submitClaim(@AuthenticationPrincipal UserDetails userDetails, Model model,
			RedirectAttributes redirectAttributes) {
		
		Card card = null;

		// Lookup the user's card by their email.
		try {
			card = cardService.findCardByEmail(userDetails.getUsername());
			model.addAttribute("card", card);

		} catch (NotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "No cards found.");

		}

		// This list populates the pharmacy dropdown in claim.html.
		List<Pharmacy> pharmacies;
		try {
			pharmacies = pharmacyService.getAllPharmacies();
			model.addAttribute("pharmacies", pharmacies);
		} catch (NotFoundException e) {}
		
		return "claim";
	}

	// Once the user submits a claim, write that claim to the database.
	@PostMapping("/claim")
	public String submitClaim(@AuthenticationPrincipal UserDetails userDetails,
			@ModelAttribute("pharmacy") @Valid ClaimSubmissionDto claimDto, Model model, BindingResult result,
			RedirectAttributes redirectAttributes) {

		// This is the user-chosen pharmacy from the dropdown.
		Pharmacy pharmacy;

		try {
			pharmacy = pharmacyService.findPharmacyById(claimDto.getPharmacyId());
			Claim claim = claimService.submitClaim(userDetails, pharmacy);

			model.addAttribute("claim", claim);
			redirectAttributes.addFlashAttribute("message", "You have successfully submitted a claim.");
			return "redirect:/claim?success";

		} catch (NotFoundException e) {
			return "userDashboard";
		}
	}

	// Returns the page to view all of the user's claims.
	@GetMapping("/claims")
	public String getAllClaims(@AuthenticationPrincipal UserDetails userDetails, Model model,
			RedirectAttributes redirectAttributes) {
		
		List<Claim> claims;
		Card card = null;

		// Lookup the user's card by their email.
		try {
			card = cardService.findCardByEmail(userDetails.getUsername());
			model.addAttribute("card", card);

		} catch (NotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "No cards found.");
		}

		// Use that card to find all the associated claims.
		try {
			claims = claimService.getAllClaimsByCard(card);
			model.addAttribute("claims", claims);
			return "claims";
		} catch (NotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "No claims found.");
			return "redirect:/userDashboard";
		}
	}

	// Return the page to delete the user account.
	@GetMapping("/delete")
	public ModelAndView deleteUser(@AuthenticationPrincipal UserDetails userDetails, Model model,
			RedirectAttributes redirectAttributes) {
		Card card = null;

		// Lookup the user's card by their email.
		try {
			card = cardService.findCardByEmail(userDetails.getUsername());
			model.addAttribute("card", card);

		} catch (NotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "No cards found.");
		}
		return new ModelAndView("delete");
	}
	
	// Deletes a user.
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteUserByEmail(@AuthenticationPrincipal UserDetails userDetails, Model model,
			RedirectAttributes redirectAttributes) {

		try {
			User user = userService.deleteUserByEmail(userDetails);
			model.addAttribute("user", user);
			redirectAttributes.addFlashAttribute("message", "Your account has been deleted.");

			// Return to the login page after the deletion.
			return "login";

		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "Your account has not been deleted.");
			return "redirect:/delete?error";
		}
	}

	// Returns the edit profile page.
	@GetMapping("/edit")
	public String showEditForm(@AuthenticationPrincipal UserDetails userDetails, Model model,
			RedirectAttributes redirectAttributes) {
		
		Card card = null;

		// Lookup the user's card by their email.
		try {
			card = cardService.findCardByEmail(userDetails.getUsername());
			model.addAttribute("card", card);
		} catch (NotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "No cards found.");
		}
		
		model.addAttribute("user", card.getUser());

		return "edit";
	}

	// Once the user submits their updated profile, save it.
	@PostMapping("/edit")
	public String editUserProfile(@ModelAttribute("user") @Valid UserProfileDto userProfileDto, BindingResult result) {

		if (result.hasErrors()) {
			return "edit";
		}

		userService.updateUser(userProfileDto);
		return "redirect:/edit?success";
	}

	// Returns the user's profile (read-only).
	@GetMapping("/profile")
	public String showUserProfile(@AuthenticationPrincipal UserDetails userDetails, Model model,
			RedirectAttributes redirectAttributes) {

		Card card = null;

		// Lookup the user's card by their email.
		try {
			card = cardService.findCardByEmail(userDetails.getUsername());
			model.addAttribute("card", card);
		} catch (NotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "No cards found.");
		}

		model.addAttribute("user", card.getUser());

		return "profile";
	}
}
