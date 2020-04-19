/**
 * 
 */
package net.paramount.auth.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import net.paramount.auth.domain.SecurityPrincipalProfile;
import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.exceptions.NgepAuthException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.entity.auth.AuthenticationDetails;
import net.paramount.framework.model.ExecutionContext;

/**
 * @author ducbq
 *
 */
public interface AuthorizationService {
	SecurityPrincipalProfile authenticate(String ssoId, String password) throws NgepAuthException;
	SecurityPrincipalProfile authenticate(String token) throws NgepAuthException;

	SecurityPrincipalProfile getActiveSecuredProfile() throws NgepAuthException;
	
	boolean hasPermission(String target, String action) throws NgepAuthException;

	SecurityAccountProfile saveSecurityAccountProfile(SecurityAccountProfile securityAccountProfile) throws NgepAuthException;

	SecurityPrincipalProfile register(ExecutionContext context) throws NgepAuthException;

	SecurityAccountProfile getUserAccount(String ssoId)  throws ObjectNotFoundException;

	SecurityPrincipalProfile confirmByToken(String token)  throws ObjectNotFoundException;

	List<AccessDecisionPolicy> getAccessDecisionPolicies(String accessPattern) throws ObjectNotFoundException;

	List<AccessDecisionPolicy> getAccessDecisionPolicies(AuthenticationDetails authenticationDetails) throws ObjectNotFoundException;

	boolean hasAccessDecisionPolicy(FilterInvocation filterInvocation, Authentication authentication);
}
