/**
 * 
 */
package net.paramount.auth.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import net.paramount.auth.domain.SecurityAccountProfile;
import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.entity.UserAccount;
import net.paramount.auth.exception.CorporateAuthenticationException;
import net.paramount.exceptions.CorporateAuthException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.entity.auth.AuthenticationDetails;
import net.paramount.framework.model.ExecutionContext;

/**
 * @author ducbq
 *
 */
public interface AuthorizationService {
	SecurityAccountProfile authenticate(String ssoId, String password) throws CorporateAuthenticationException;
	SecurityAccountProfile authenticate(String token) throws CorporateAuthenticationException;

	SecurityAccountProfile getUserProfile() throws CorporateAuthenticationException;
	
	boolean hasPermission(String target, String action) throws CorporateAuthenticationException;

	SecurityAccountProfile register(ExecutionContext context) throws CorporateAuthException;

	UserAccount getUserAccount(String ssoId)  throws ObjectNotFoundException;

	UserAccount confirmByToken(String token)  throws ObjectNotFoundException;

	List<AccessDecisionPolicy> getAccessDecisionPolicies(String accessPattern) throws ObjectNotFoundException;

	List<AccessDecisionPolicy> getAccessDecisionPolicies(AuthenticationDetails authenticationDetails) throws ObjectNotFoundException;

	boolean hasAccessDecisionPolicy(FilterInvocation filterInvocation, Authentication authentication);

	SecurityAccountProfile getActiveSecurityAccountProfile();
}
