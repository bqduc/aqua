/**
 * 
 */
package net.paramount.auth.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import net.paramount.auth.domain.SecurityPrincipalProfile;
import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.entity.UserAccount;
import net.paramount.exceptions.CorporateAuthException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.entity.auth.AuthenticationDetails;
import net.paramount.framework.model.ExecutionContext;

/**
 * @author ducbq
 *
 */
public interface AuthorizationService {
	SecurityPrincipalProfile authenticate(String ssoId, String password) throws CorporateAuthException;
	SecurityPrincipalProfile authenticate(String token) throws CorporateAuthException;

	SecurityPrincipalProfile getSecuredProfile() throws CorporateAuthException;
	
	boolean hasPermission(String target, String action) throws CorporateAuthException;

	SecurityPrincipalProfile register(ExecutionContext context) throws CorporateAuthException;

	UserAccount getUserAccount(String ssoId)  throws ObjectNotFoundException;

	SecurityPrincipalProfile confirmByToken(String token)  throws ObjectNotFoundException;

	List<AccessDecisionPolicy> getAccessDecisionPolicies(String accessPattern) throws ObjectNotFoundException;

	List<AccessDecisionPolicy> getAccessDecisionPolicies(AuthenticationDetails authenticationDetails) throws ObjectNotFoundException;

	boolean hasAccessDecisionPolicy(FilterInvocation filterInvocation, Authentication authentication);
}
