/**
 * 
 */
package net.paramount.controller.security;

import java.io.InputStream;
import java.util.Base64;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Component;

import net.paramount.auth.domain.UserSecurityProfile;
import net.paramount.auth.service.AuthorizationService;
import net.paramount.common.CommonConstants;
import net.paramount.common.CommonUtility;
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

	public UserSecurityProfile getSecurityAccountProfile() {
		UserSecurityProfile securityAccountProfile = null;
		try {
			securityAccountProfile = (UserSecurityProfile)this.httpSession.getAttribute(GlobalConstants.AUTHENTICATED_PROFILE);
		} catch (Exception e) {
			log.error(e);
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

	public String getUserProfileImageContentsAsBase64() {
		InputStream inputStream = null;
		String imageContentsAsBase64 = null;
		byte[] imageBytes = null;
		try {
			UserSecurityProfile securityPrincipalProfile = this.getSecurityAccountProfile();
			if (null==securityPrincipalProfile || null==securityPrincipalProfile.getUserAccount() || null == securityPrincipalProfile.getUserAccount().getProfilePicture()) {
				inputStream = servletContext.getResourceAsStream("/resources/images/anonymous-user-small.png");
				imageBytes = CommonUtility.getByteArray(inputStream);
				imageContentsAsBase64 = Base64.getEncoder().encodeToString(imageBytes);
			} else {
				imageContentsAsBase64 = Base64.getEncoder().encodeToString(securityPrincipalProfile.getUserAccount().getProfilePicture());
			}
		} catch (Exception e) {
			log.error(e);
		}
    return imageContentsAsBase64;
	}
}
