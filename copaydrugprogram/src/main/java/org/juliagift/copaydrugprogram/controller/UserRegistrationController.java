package org.juliagift.copaydrugprogram.controller;

import javax.validation.Valid;

import org.juliagift.copaydrugprogram.dto.UserRegistrationDto;
import org.juliagift.copaydrugprogram.model.User;
import org.juliagift.copaydrugprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	@Autowired
	private UserService userService;
	

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}
	
	// Return registration form template
	@GetMapping
	public String showRegistrationForm(Model model) {
		
		return "registration";
	}
	
	// Process form input data
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
			BindingResult result) {
		
		// Lookup patient in database by email
		User existingUser = userService.findUserByEmail(userDto.getEmail());
		if (existingUser != null) {
			result.rejectValue("email", null, "There is already an account registered with that email");
			
		}
		
		if (result.hasErrors()) {
			return "registration";
		}
		

		userService.registerUser(userDto);
		return "redirect:/registration?success";

	}
	
	

}
