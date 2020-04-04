package net.paramount.auth.intercept;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import net.paramount.auth.domain.SecurityPrincipalProfile;
import net.paramount.auth.service.AuthorizationService;
import net.paramount.common.CommonUtility;
import net.paramount.exceptions.EcosphereAuthException;
import net.paramount.framework.component.CompCore;
import net.paramount.global.GlobalConstants;

/**
 * Created by aLeXcBa1990 on 24/11/2018.
 * 
 */
@Component(value="authenticationProvider")
public class CustomAuthenticationProvider extends CompCore implements AuthenticationProvider {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8355652678792800184L;

	@Inject
	ObjectFactory<HttpSession> httpSessionFactory;
	
	@Inject 
	private AuthorizationService authorizationService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Authentication authenticateResp = null;
		try {
			if (authentication.getName().length() < 150) {
				authenticateResp = authenticateBySsoId(authentication.getName(), authentication.getCredentials().toString());
			} else {
				authenticateResp = authenticateByToken(authentication.getName());
			}
		} catch (EcosphereAuthException cae) {
			throw cae;
		}
		return authenticateResp;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	private Authentication authenticateBySsoId(String ssoId, String password) throws EcosphereAuthException {
		Authentication authObject = null;
		SecurityPrincipalProfile userAccountProfile = null;
		try {
			userAccountProfile = authorizationService.authenticate(ssoId, password);
			if (null != userAccountProfile) {
				authObject = new UsernamePasswordAuthenticationToken(ssoId, password, userAccountProfile.getUserAccount().getAuthorities());
				userAccountProfile.setAuthentication(authObject);

				httpSessionFactory.getObject().setAttribute(GlobalConstants.AUTHENTICATED_PROFILE, userAccountProfile);
			}
		} catch (Exception uae) {
			throw new EcosphereAuthException(uae);
		}

		return authObject;
	}

	private Authentication authenticateByToken(String token) throws EcosphereAuthException {
		Authentication authByToken = null;
		SecurityPrincipalProfile userAccountProfile = null;
		try {
			userAccountProfile = authorizationService.authenticate(token);
			if (null != userAccountProfile) {
				authByToken = new UsernamePasswordAuthenticationToken(token, CommonUtility.STRING_BLANK, userAccountProfile.getUserAccount().getAuthorities());			
			}
		} catch (Exception e) {
			throw new EcosphereAuthException(e);
		}

		return authByToken;
	}
}
