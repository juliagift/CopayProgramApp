package org.juliagift.copaydrugprogram.configuration;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/*
The user-based redirect is configured using the customized Success Handler 
for spring boot security. Create a Login Success Handler class with AuthenticationSuccessHandler. 
The AuthenticationSuccessHandler interface will be called for successful login authentication. 
It redircts the user to the their respective dashboard pages based on their role.
*/

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String redirectUrl = null;
	
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			
			if (grantedAuthority.getAuthority().equals("USER")) { // Users go to the User Dashboard.
				redirectUrl = "/userDashboard";
				break;
			} else if (grantedAuthority.getAuthority().equals("ADMIN")) { // Admins go to the Admin Dashboard.
				redirectUrl = "/adminDashboard";
				break;
			}
		}
				
		if (redirectUrl == null) {
			throw new IllegalStateException();
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}
}
