/**
 * 
 */
package net.paramount.auth.intercept;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import net.paramount.exceptions.EcosExceptionCode;
import net.paramount.exceptions.EcosphereAuthException;
import net.paramount.framework.logging.LogService;

/**
 * @author ducbq
 *
 */
@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Inject 
	private LogService log;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		StringBuilder message = new StringBuilder("/login.xhtml?authfailed=true");
		EcosphereAuthException cae = null;
		if (exception.getCause() instanceof EcosphereAuthException) {
			cae = (EcosphereAuthException) exception.getCause();
			request.getSession().setAttribute("authenticateResultAtt", generateAuthMessage(cae.getAuthenticationCode()));
		}

		try {
			response.sendRedirect(message.toString());
		} catch (Exception e) {
			log.error(e);
		}
	}

	private String generateAuthMessage(EcosExceptionCode authenticationCode) {
		return authenticationCode.getMessage();
	}
}
