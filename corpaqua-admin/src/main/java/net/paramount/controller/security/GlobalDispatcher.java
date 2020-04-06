/**
 * 
 */
package net.paramount.controller.security;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

import net.paramount.auth.domain.SecurityPrincipalProfile;
import net.paramount.auth.service.AuthorizationService;
import net.paramount.framework.controller.RootController;
import net.paramount.global.GlobalConstants;

/**
 * @author ducbq
 *
 */
@Component
@Named(value = "globalDispatcher")
//@ViewScoped
@SessionScoped
public class GlobalDispatcher extends RootController {
	private static final long serialVersionUID = -4189926376687700775L;

	@Inject
	private AuthorizationService authorizationService;

	private String failureMessage;

	public SecurityPrincipalProfile getSecurityAccountProfile() {
		SecurityPrincipalProfile securityAccountProfile = null;
		try {
			securityAccountProfile = (SecurityPrincipalProfile)this.httpSession.getAttribute(GlobalConstants.AUTHENTICATED_PROFILE);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		if (null == securityAccountProfile) {
			securityAccountProfile = authorizationService.getActiveSecuredProfile();
		}
		
		return securityAccountProfile;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
}
