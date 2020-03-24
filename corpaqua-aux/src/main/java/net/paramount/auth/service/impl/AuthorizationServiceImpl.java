/**
 * 
 */
package net.paramount.auth.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import net.paramount.auth.component.JwtTokenProvider;
import net.paramount.auth.domain.SecurityAccountProfile;
import net.paramount.auth.entity.AccessDecisionPolicy;
import net.paramount.auth.entity.Authority;
import net.paramount.auth.entity.UserAccount;
import net.paramount.auth.exception.CorporateAuthenticationException;
import net.paramount.auth.service.AccessDecisionPolicyService;
import net.paramount.auth.service.AuthorityService;
import net.paramount.auth.service.AuthorizationService;
import net.paramount.auth.service.UserAccountService;
import net.paramount.autx.SecurityServiceContextHelper;
import net.paramount.comm.comp.Communicator;
import net.paramount.comm.domain.CorpMimeMessage;
import net.paramount.comm.global.CommunicatorConstants;
import net.paramount.common.DateTimeUtility;
import net.paramount.common.ListUtility;
import net.paramount.exceptions.CorporateAuthException;
import net.paramount.exceptions.ObjectNotFoundException;
import net.paramount.framework.entity.auth.AuthenticationDetails;
import net.paramount.framework.model.ExecutionContext;
import net.paramount.global.GlobalConstants;

/**
 * @author ducbq
 *
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
	@Inject
	private Communicator emailCommunicator;

	@Inject
	private UserAccountService userAccountService;

	@Inject 
	private SecurityServiceContextHelper securityContextHolderServiceHelper;
	
	@Inject
	private JwtTokenProvider tokenProvider;

	@Inject
	private AuthorityService authorityService;

	@Inject
	private AccessDecisionPolicyService accessDecisionPolicyService;

	@Override
	public SecurityAccountProfile authenticate(String ssoId, String password) throws CorporateAuthenticationException {
		UserAccount fetchedUserAccount = this.userAccountService.getUserAccount(ssoId, password);
		return getAuthenticatedUserProfile(fetchedUserAccount);
	}

	@Override
	public SecurityAccountProfile authenticate(String loginToken) throws CorporateAuthenticationException {
		UserAccount fetchedUserAccount = this.userAccountService.getUserAccount(loginToken);
		return getAuthenticatedUserProfile(fetchedUserAccount);
	}

	private SecurityAccountProfile getAuthenticatedUserProfile(UserAccount userAccount) {
		return SecurityAccountProfile.builder()
		.userAccount(userAccount)
		.build();
	}

	@Override
	public SecurityAccountProfile getUserProfile() throws CorporateAuthenticationException {
		SecurityAccountProfile fetchedResponse = null;
		Object systemPrincipal = getSystemPrincipal();
		if (systemPrincipal instanceof User || systemPrincipal instanceof UserAccount) {
			UserAccount userAccount = this.userAccountService.get(((User)systemPrincipal).getUsername());
			fetchedResponse = SecurityAccountProfile
			.builder()
			.userAccount(userAccount)
			.build();
		} else if (systemPrincipal instanceof String){ //Anonymous user - Not logged in
			fetchedResponse = SecurityAccountProfile.builder()
			.displayName((String)systemPrincipal)
			.build();
		}
		return fetchedResponse;
	}

	private Object getSystemPrincipal() {
		Authentication authentication = securityContextHolderServiceHelper.getAuthentication();
		if (null==authentication)
			return null;

		if (authentication.getPrincipal() instanceof String) {
			return authentication.getPrincipal();
		}

		return authentication.getPrincipal();
	}

	@Override
	public boolean hasPermission(String target, String action) throws CorporateAuthenticationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SecurityAccountProfile register(ExecutionContext context) throws CorporateAuthException {
		String confirmLink = null;
		UserAccount updatedUserAccount = null;
		SecurityAccountProfile registrationProfile = null;
		CorpMimeMessage mimeMessage = null;
		try {
			updatedUserAccount = (UserAccount)context.get(CommunicatorConstants.CTX_USER_ACCOUNT);
			updatedUserAccount.setRegisteredDate(DateTimeUtility.getSystemDateTime());

			updatedUserAccount = userAccountService.save(updatedUserAccount);

			updatedUserAccount.setActivationKey(tokenProvider.generateToken(updatedUserAccount));
			updatedUserAccount = userAccountService.saveOrUpdate(updatedUserAccount);

			mimeMessage = (CorpMimeMessage)context.get(CommunicatorConstants.CTX_MIME_MESSAGE);
			if (null==mimeMessage) {
				mimeMessage = CorpMimeMessage.builder()
						.subject(CommunicatorConstants.CTX_DEFAULT_REGISTRATION_SUBJECT)
						.recipients(new String[] {updatedUserAccount.getEmail()})
						.build();
			}
			mimeMessage.setRecipients(new String[] {updatedUserAccount.getEmail()});
			mimeMessage.getDefinitions().put(CommunicatorConstants.CTX_USER_TOKEN, updatedUserAccount.getActivationKey());

			confirmLink = (String)mimeMessage.getDefinitions().get(GlobalConstants.CONFIG_APP_ACCESS_URL);
			mimeMessage.getDefinitions().put(CommunicatorConstants.CTX_USER_CONFIRM_LINK, new StringBuilder(confirmLink).append(updatedUserAccount.getActivationKey()).toString());

			context.put(CommunicatorConstants.CTX_MIME_MESSAGE, mimeMessage);
			registrationProfile = SecurityAccountProfile.builder()
					.displayName(updatedUserAccount.getDisplayName())
					.userAccount(updatedUserAccount)
					.build();

			emailCommunicator.send(context);
		} catch (Exception e) {
			throw new CorporateAuthException(e);
		}
		return registrationProfile;
	}

	@Override
	public UserAccount getUserAccount(String ssoId) throws ObjectNotFoundException {
		return userAccountService.get(ssoId);
	}

	@Override
	public UserAccount confirmByToken(String token) throws ObjectNotFoundException {
		UserAccount confirnUserAccount = null;
		AuthenticationDetails userDetails = tokenProvider.getUserDetailsFromJWT(token);
		if (userDetails != null) {
			confirnUserAccount = this.getUserAccount(userDetails.getSsoId());
		}

		confirnUserAccount.addPrivilege(authorityService.getMinimumUserAuthority());
		confirnUserAccount.setActivated(Boolean.TRUE);
		confirnUserAccount.setVisible(Boolean.TRUE);
		confirnUserAccount.setActivationDate(DateTimeUtility.getSystemDateTime());

		userAccountService.save(confirnUserAccount);
		return confirnUserAccount;
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

	@Override
	public SecurityAccountProfile getActiveSecurityAccountProfile() {
		this.securityContextHolderServiceHelper.getAuthenticationPrincipal();
		return null;
	}
}
