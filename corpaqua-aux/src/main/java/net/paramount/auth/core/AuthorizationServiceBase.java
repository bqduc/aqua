/**
 * 
 */
package net.paramount.auth.core;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import net.paramount.auth.domain.SecurityPrincipalProfile;
import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.auth.entity.UserAccountPrivilege;
import net.paramount.auth.service.UserAccountService;
import net.paramount.common.CommonConstants;
import net.paramount.common.CommonUtility;
import net.paramount.exceptions.EcosExceptionCode;
import net.paramount.exceptions.EcosphereAuthException;

/**
 * @author ducbq
 *
 */
public abstract class AuthorizationServiceBase {
	@Inject
	protected UserAccountService userAccountService;

	protected SecurityPrincipalProfile generateSecurityPrincipalProfile(String authenticateToken, String password) throws EcosphereAuthException {
		SecurityAccountProfile userAccount = null;
		SecurityPrincipalProfile securityAccountProfile = null;
		if (CommonUtility.isEmpty(password)) {
			userAccount = this.userAccountService.getUserAccount(authenticateToken);
		} else {
			userAccount = this.userAccountService.getUserAccount(authenticateToken, password);
		}

		if (null==userAccount)
			throw new EcosphereAuthException(EcosExceptionCode.ERROR_INVALID_PROFILE, "There is empty authentication user account");

		securityAccountProfile = SecurityPrincipalProfile.builder()
		.userAccount(userAccount)
		.build();
		for (UserAccountPrivilege userAccountPrivilege :userAccount.getPrivileges()) {
			securityAccountProfile.addGrantedAuthority(userAccountPrivilege.getAuthority());
		}

		return securityAccountProfile;
	}

	protected SecurityPrincipalProfile getCurrentSecuredProfile() throws EcosphereAuthException {
		SecurityPrincipalProfile fetchedResponse = null;
		Object systemPrincipal = getSystemPrincipal();
		SecurityAccountProfile userAccount = null;
		if (systemPrincipal instanceof User || systemPrincipal instanceof SecurityAccountProfile) {
			userAccount = this.userAccountService.get(((User)systemPrincipal).getUsername());
			fetchedResponse = SecurityPrincipalProfile
			.builder()
			.displayName(new StringBuilder(userAccount.getFirstName()).append(CommonConstants.STRING_SPACE).append(userAccount.getLastName()).toString())
			.userAccount(userAccount)
			.build();
		} else if (systemPrincipal instanceof String){ //Anonymous user - Not logged in
			if (CommonConstants.ANONYMOUS_USER.equalsIgnoreCase((String)systemPrincipal)) {
				fetchedResponse = SecurityPrincipalProfile.builder()
						.displayName((String)systemPrincipal)
						.build();
			} else {
				userAccount = this.userAccountService.get((String)systemPrincipal);
				fetchedResponse = SecurityPrincipalProfile
				.builder()
				.displayName(new StringBuilder(userAccount.getFirstName()).append(CommonConstants.STRING_SPACE).append(userAccount.getLastName()).toString())
				.userAccount(userAccount)
				.build();
			}
		}
		return fetchedResponse;
	}

	protected Authentication getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication;
	}

	protected Object getAuthenticationPrincipal() {
		Authentication authentication = this.getAuthentication();
		return (null != authentication)? authentication.getPrincipal():null;
	}

	protected Object getSystemPrincipal() {
		Authentication authentication = this.getAuthentication();
		if (null==authentication)
			return null;

		if (authentication.getPrincipal() instanceof String) {
			return authentication.getPrincipal();
		}

		return authentication.getPrincipal();
	}

}
