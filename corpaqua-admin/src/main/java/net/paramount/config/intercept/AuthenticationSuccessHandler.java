/**
 * 
 */
package net.paramount.config.intercept;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import net.paramount.auth.entity.UserAccount;
import net.paramount.auth.service.AuthorizationService;

/**
 * @author ducbq
 *
 */
@Service
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Inject 
	private AuthorizationService authorizationService;
	
	//private RequestCache requestCache;

	public AuthenticationSuccessHandler() {
		//this.requestCache = new HttpSessionRequestCache();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		//User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserAccount userAccount = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
			userAccount = authorizationService.getUserAccount((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		}
		request.getSession().setAttribute("userDetails", userAccount);
		super.onAuthenticationSuccess(request, response, authentication);
	}
}