/**
 * 
 */
package net.paramount.auth.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import net.paramount.auth.comp.JwtTokenProvider;
import net.paramount.auth.core.AuthorizationServiceBase;
import net.paramount.auth.domain.SecurityPrincipalProfile;
import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.entity.Authority;
import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.auth.service.AccessDecisionPolicyService;
import net.paramount.auth.service.AuthorityService;
import net.paramount.auth.service.AuthorizationService;
import net.paramount.autx.SecurityServiceContextHelper;
import net.paramount.comm.comp.Communicator;
import net.paramount.comm.domain.CorpMimeMessage;
import net.paramount.comm.global.CommunicatorConstants;
import net.paramount.common.DateTimeUtility;
import net.paramount.common.ListUtility;
import net.paramount.exceptions.EcosphereAuthException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.entity.auth.AuthenticationDetails;
import net.paramount.framework.model.ExecutionContext;
import net.paramount.global.GlobalConstants;

/**
 * @author ducbq
 *
 */
@Service
public class AuthorizationServiceImpl extends AuthorizationServiceBase implements AuthorizationService {
	@Inject
	private Communicator emailCommunicator;

	@Inject 
	private SecurityServiceContextHelper securityContextHolderServiceHelper;
	
	@Inject
	private JwtTokenProvider tokenProvider;

	@Inject
	private AuthorityService authorityService;

	@Inject
	private AccessDecisionPolicyService accessDecisionPolicyService;

	@Override
	public SecurityPrincipalProfile authenticate(String ssoId, String password) throws EcosphereAuthException {
		return this.generateSecurityPrincipalProfile(ssoId, password);
	}

	@Override
	public SecurityPrincipalProfile authenticate(String loginToken) throws EcosphereAuthException {
		return this.generateSecurityPrincipalProfile(loginToken, null);
	}

	@Override
	public SecurityPrincipalProfile getSecuredProfile() throws EcosphereAuthException {
		return this.getCurrentSecuredProfile();
	}

	@Override
	public boolean hasPermission(String target, String action) throws EcosphereAuthException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SecurityPrincipalProfile register(ExecutionContext context) throws EcosphereAuthException {
		String confirmLink = null;
		SecurityPrincipalProfile registrationProfile = null;
		CorpMimeMessage mimeMessage = null;
		try {
			registrationProfile = userAccountService.register((SecurityAccountProfile)context.get(CommunicatorConstants.CTX_USER_ACCOUNT));

			mimeMessage = (CorpMimeMessage)context.get(CommunicatorConstants.CTX_MIME_MESSAGE);
			if (null==mimeMessage) {
				mimeMessage = CorpMimeMessage.builder()
						.subject(CommunicatorConstants.CTX_DEFAULT_REGISTRATION_SUBJECT)
						.recipients(new String[] {registrationProfile.getUserAccount().getEmail()})
						.build();
			}
			mimeMessage.setRecipients(new String[] {registrationProfile.getUserAccount().getEmail()});
			mimeMessage.getDefinitions().put(CommunicatorConstants.CTX_USER_TOKEN, registrationProfile.getUserAccount().getActivationKey());

			confirmLink = (String)mimeMessage.getDefinitions().get(GlobalConstants.CONFIG_APP_ACCESS_URL);
			mimeMessage.getDefinitions().put(CommunicatorConstants.CTX_USER_CONFIRM_LINK, new StringBuilder(confirmLink).append(registrationProfile.getUserAccount().getActivationKey()).toString());

			context.put(CommunicatorConstants.CTX_MIME_MESSAGE, mimeMessage);

			emailCommunicator.send(context);
		} catch (Exception e) {
			throw new EcosphereAuthException(e);
		}
		return registrationProfile;
	}

	@Override
	public SecurityAccountProfile getUserAccount(String ssoId) throws ObjectNotFoundException {
		return userAccountService.get(ssoId);
	}

	@Override
	public SecurityPrincipalProfile confirmByToken(String token) throws ObjectNotFoundException {
		SecurityPrincipalProfile confirmedSecurityAccountProfile = SecurityPrincipalProfile.builder().build();
		SecurityAccountProfile confirnUserAccount = null;
		AuthenticationDetails userDetails = tokenProvider.getUserDetailsFromJWT(token);
		if (userDetails != null) {
			confirnUserAccount = this.getUserAccount(userDetails.getSsoId());
		}

		confirnUserAccount.addPrivilege(authorityService.getMinimumUserAuthority());
		confirnUserAccount.setActivated(Boolean.TRUE);
		confirnUserAccount.setVisible(Boolean.TRUE);
		confirnUserAccount.setActivationDate(DateTimeUtility.getSystemDateTime());

		userAccountService.save(confirnUserAccount);
		confirmedSecurityAccountProfile.setUserAccount(confirnUserAccount);
		return confirmedSecurityAccountProfile;
	}

	@Override
	public List<AccessDecisionPolicy> getAccessDecisionPolicies(String accessPattern) throws ObjectNotFoundException {
		return accessDecisionPolicyService.getAccessDecisionPolicies(accessPattern);
	}

	@Override
	public List<AccessDecisionPolicy> getAccessDecisionPolicies(AuthenticationDetails authenticationDetails) throws ObjectNotFoundException {
		List<AccessDecisionPolicy> accessDecisionPolicies = ListUtility.createDataList();
		List<AccessDecisionPolicy> currentADPs = null; 
		for (GrantedAuthority authority :authenticationDetails.getAuthorities()) {
			currentADPs = accessDecisionPolicyService.getAccessDecisionPoliciesByAuthority((Authority)authority);
			if (!currentADPs.isEmpty()) {
				accessDecisionPolicies.addAll(currentADPs);
			}
		}
		return accessDecisionPolicies;
	}

	@Override
	public boolean hasAccessDecisionPolicy(FilterInvocation filterInvocation, Authentication authentication) {
		final String MY_ACCESSED_DECISION_POLICIES = "myAccessedDecisionPolicies";
		boolean hasAccessedPermission = false;
		List<AccessDecisionPolicy> accessDecisionPolicies = null;
		List<AccessDecisionPolicy> currentADPs = null; 
		PathMatcher pathMatcher = null;

		accessDecisionPolicies = (List<AccessDecisionPolicy>)filterInvocation.getHttpRequest().getSession(false).getAttribute(MY_ACCESSED_DECISION_POLICIES);
    if (null==accessDecisionPolicies) {
    	accessDecisionPolicies = ListUtility.createDataList();
  		for (GrantedAuthority authority :authentication.getAuthorities()) {
  			currentADPs = accessDecisionPolicyService.getAccessDecisionPoliciesByAuthority((Authority)authority);
  			if (!currentADPs.isEmpty()) {
  				accessDecisionPolicies.addAll(currentADPs);
  			}
  		}

  		filterInvocation.getHttpRequest().getSession(false).setAttribute(MY_ACCESSED_DECISION_POLICIES, accessDecisionPolicies);
    }

		pathMatcher = new AntPathMatcher();
		for (AccessDecisionPolicy accessDecisionPolicy :accessDecisionPolicies) {
			if (pathMatcher.match(accessDecisionPolicy.getAccessPattern(), filterInvocation.getRequestUrl())) {
				hasAccessedPermission = true;
			}
		}

		return hasAccessedPermission;
	}
}
