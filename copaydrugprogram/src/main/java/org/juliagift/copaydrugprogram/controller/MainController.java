package org.juliagift.copaydrugprogram.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.juliagift.copaydrugprogram.model.Login;
import org.juliagift.copaydrugprogram.model.Role;
import org.juliagift.copaydrugprogram.model.User;
import org.juliagift.copaydrugprogram.service.RoleService;
import org.juliagift.copaydrugprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;
	
	// Return the home page.
	@GetMapping("/home")
	public String showHomePage() {
		return "home";
	}
	
	// Return the login page.
	@GetMapping("/login")
	public  ModelAndView showLoginPage() {
		// Check to see if there is an admin account.
		// If not, add one.
		Role role = null;
		
		try {
			role = roleService.findByName("ADMIN");
		} catch (NotFoundException e) {}

		if (role == null) {
			initializeAdmin();
		} 

		return new ModelAndView("login");
	}
	
	// If there are no admin users in the database, create one.
	
	/********************************
	 * THIS IS AN INSERT OPERATION. *
	 ********************************/
	public User initializeAdmin() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date dob = null;
		
		try {
			dob = simpleDateFormat.parse("1980-02-29");
		} catch (ParseException e) {}
	
		Login login = new Login("pj@julia.com", passwordEncoder.encode("poppypass"));
	
		Collection<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ADMIN"));
	
		User user = new User("Poppy", "Johnson", dob, "female", "6238743764", "57 Almond Ave", "Bldg 3", "Chandler", "AZ", "85224", "4233", login, roles);
		
		userService.saveUser(user);
		
		return user;
	}
}
