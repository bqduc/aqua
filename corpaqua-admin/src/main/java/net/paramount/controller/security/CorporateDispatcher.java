/**
 * 
 */
package net.paramount.controller.security;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import net.paramount.auth.domain.SecurityAccountProfile;
import net.paramount.framework.controller.RootController;

/**
 * @author ducbq
 *
 */
@Named(value = "corpDispatcher")
@ViewScoped
public class CorporateDispatcher extends RootController {
	private static final long serialVersionUID = -4189926376687700775L;

	public SecurityAccountProfile getSecurityAccountProfile() {
		SecurityAccountProfile securityAccountProfile = SecurityAccountProfile.builder().build();
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (null==securityContext) {
			this.log.info("There is no security context object. ");
			return securityAccountProfile;
		}

		return securityAccountProfile;
	}
}
