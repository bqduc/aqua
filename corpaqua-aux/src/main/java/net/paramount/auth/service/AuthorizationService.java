/**
 * 
 */
package net.paramount.auth.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import net.paramount.auth.domain.UserSecurityProfile;
import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.entity.UserAccountProfile;
import net.paramount.exceptions.NgepAuthException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.entity.auth.AuthenticationDetails;
import net.paramount.framework.model.ExecutionContext;

/**
 * @author ducbq
 *
 */
public interface AuthorizationService {
	UserSecurityProfile authenticate(String ssoId, String password) throws NgepAuthException;
	UserSecurityProfile authenticate(String token) throws NgepAuthException;

	UserSecurityProfile getActiveSecuredProfile() throws NgepAuthException;
	
	boolean hasPermission(String target, String action) throws NgepAuthException;

	UserAccountProfile saveSecurityAccountProfile(UserAccountProfile securityAccountProfile) throws NgepAuthException;

	UserSecurityProfile register(ExecutionContext context) throws NgepAuthException;

	UserAccountProfile getUserAccount(String ssoId)  throws ObjectNotFoundException;

	UserSecurityProfile confirmByToken(String token)  throws ObjectNotFoundException;

	List<AccessDecisionPolicy> getAccessDecisionPolicies(String accessPattern) throws ObjectNotFoundException;

	List<AccessDecisionPolicy> getAccessDecisionPolicies(AuthenticationDetails authenticationDetails) throws ObjectNotFoundException;

	boolean hasAccessDecisionPolicy(FilterInvocation filterInvocation, Authentication authentication);
}
