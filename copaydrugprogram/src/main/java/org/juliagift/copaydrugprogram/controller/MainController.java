package org.juliagift.copaydrugprogram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	
	@GetMapping("/home")
	public String showHomePage() {
		return "home";
	}
	
	@GetMapping("/login")
	public  ModelAndView showLoginPage() {
		return new ModelAndView("login");
	}
	
	@GetMapping("/delete")
	public ModelAndView deleteUser() {
		return new ModelAndView("delete");
	}
	
}
