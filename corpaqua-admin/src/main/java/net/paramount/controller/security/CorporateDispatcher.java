/**
 * 
 */
package net.paramount.controller.security;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FileUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ResourceUtils;

import com.github.adminfaces.template.config.AdminConfig;

import net.paramount.auth.domain.SecurityAccountProfile;
import net.paramount.auth.entity.UserAccount;
import net.paramount.comm.comp.Communicator;
import net.paramount.comm.comp.CommunicatorServiceHelper;
import net.paramount.comm.domain.CorpMimeMessage;
import net.paramount.common.CommonConstants;
import net.paramount.common.DateTimeUtility;
import net.paramount.exceptions.CommunicatorException;
import net.paramount.framework.controller.RootController;
import net.paramount.msp.util.Constants;

/**
 * @author ducbq
 *
 */
@Named(value = "corporateDispatcher")
@ViewScoped
public class CorporateDispatcher extends RootController {
	private static final long serialVersionUID = -4189926376687700775L;

	@Inject
	private AdminConfig adminConfig;

	@Inject
	private Communicator communicationService;
	
	public SecurityAccountProfile getSecurityAccountProfile() {
		SecurityAccountProfile securityAccountProfile = SecurityAccountProfile.builder().build();
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (null==securityContext) {
			this.log.info("There is no security context object. ");
			return securityAccountProfile;
		}

		/*
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority()))
				return true;
		}
		*/
		String loginPage = adminConfig.getLoginPage();
		if (loginPage == null || "".equals(loginPage)) {
			loginPage = Constants.DEFAULT_INDEX_PAGE;
		}
		
		if (!loginPage.startsWith("/")) {
			loginPage = "/" + loginPage;
		}
		return securityAccountProfile;
	}
}
