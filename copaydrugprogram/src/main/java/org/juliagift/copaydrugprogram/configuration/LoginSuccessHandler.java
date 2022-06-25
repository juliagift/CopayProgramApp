package org.juliagift.copaydrugprogram.configuration;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/*
The user-based redirect is configured using the customized Success Handler 
for spring boot security. Create a Login Success Handler class with AuthenticationSuccessHandler. 
The AuthenticationSuccessHandler interface will be called for successful login authentication. 
Add the logic to redirect the user’s role to the respective dashboard pages.
*/

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String redirectUrl = null;
	
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			
			System.out.println("I am here in the custom success handler");
			
			System.out.println("role " + grantedAuthority.getAuthority());
			
			if (grantedAuthority.getAuthority().equals("USER")) {
				redirectUrl = "/userDashboard";
				break;
			} else if (grantedAuthority.getAuthority().equals("ADMIN")) {
				redirectUrl = "/adminDashboard";
				break;
			}
		}
		
		System.out.println("redirectUrl " + redirectUrl);
		
		if (redirectUrl == null) {
			throw new IllegalStateException();
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
		
	}

}


