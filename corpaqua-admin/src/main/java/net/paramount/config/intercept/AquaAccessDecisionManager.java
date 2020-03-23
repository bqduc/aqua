/**
 * 
 */
package net.paramount.config.intercept;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author ducbq
 *
 */
public class AquaAccessDecisionManager implements AccessDecisionManager {
	@Inject
	private AquaFilterInvocationSecurityMetadataSource securityMetadataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//	if(securityMetadataSource.isResourceMapEmpty()){
//		securityMetadataSource.refreshResource();
//	}

		if (configAttributes == null)
			throw new AccessDeniedException("Sorry, you don't have this permission.");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date()) + ":\t" + object.toString());

		for (ConfigAttribute ca : configAttributes) {
			String needRole = ca.getAttribute();
			for (GrantedAuthority userGA : authentication.getAuthorities()) {
				if (needRole.equals(userGA.getAuthority())) { // ga is user's role.
					return;
				}
			}
		}
		throw new AccessDeniedException("Sorry, you don't have this permission.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.access.AccessDecisionManager#supports(org.springframework.security.access.ConfigAttribute)
	 */
	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.access.AccessDecisionManager#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

}
