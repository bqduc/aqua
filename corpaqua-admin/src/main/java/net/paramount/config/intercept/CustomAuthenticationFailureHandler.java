/**
 * 
 */
package net.paramount.config.intercept;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import net.paramount.exceptions.AuthenticationCode;
import net.paramount.exceptions.CorporateAuthException;
import net.paramount.framework.logging.LogService;

/**
 * @author ducbq
 *
 */
/*@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Inject 
	private LogService log;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		StringBuilder message = new StringBuilder("/login.xhtml?authfailed=true");
		if (exception.getCause() instanceof CorporateAuthException) {
			CorporateAuthException cae = (CorporateAuthException) exception.getCause();
			request.getSession().setAttribute("authenticateResultAtt", generateAuthMessage(cae.getAuthenticationCode()));
		}

		try {
			response.sendRedirect(message.toString());
		} catch (Exception e) {
			log.error(e);
		}
	}

	private String generateAuthMessage(AuthenticationCode authenticationCode) {
		return authenticationCode.getMessage();
	}
}
*/