/**
 * 
 */
package net.paramount.config;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import net.paramount.auth.domain.UserSecurityProfile;
import net.paramount.auth.service.AuthorizationService;
import net.paramount.common.CommonConstants;
import net.paramount.framework.component.CompCore;

/**
 * @author ducbq
 *
 */
@Configuration
//@EnableJpaAuditing
public class AuditingConfiguration extends CompCore {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2746650235814978755L;

	@Inject 
	private AuthorizationService authorizationService;

	private UserSecurityProfile getUserSecurityProfile() {
		UserSecurityProfile securityProfile = null;
		if (authorizationService != null) {
			securityProfile = authorizationService.getActiveSecuredProfile();
		}
		return securityProfile;
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

	/*
	@Bean
	public AuditorAware<Long> auditorProvider() {
		return new AuditorAwareByKeyImpl();
	}
	*/

	class AuditorAwareImpl implements AuditorAware<String> {
		@Override
		public Optional<String> getCurrentAuditor() {
			UserSecurityProfile userSecurityProfile = getUserSecurityProfile();

			if (null != userSecurityProfile && null != userSecurityProfile.getUserAccount()) {
				return Optional.of(userSecurityProfile.getUserAccount().getSsoId());
			}

			return Optional.of(CommonConstants.ANONYMOUS_USER);//None-Anonymous;//Optional.empty();
		}
	}

	class AuditorAwareByKeyImpl implements AuditorAware<Long> {
		@Override
		public Optional<Long> getCurrentAuditor() {
			UserSecurityProfile securityPrincipalProfile = getUserSecurityProfile();

			if (null != securityPrincipalProfile && null != securityPrincipalProfile.getUserAccount()) {
				return Optional.of(securityPrincipalProfile.getUserAccount().getId());
			}

			return Optional.of(CommonConstants.ANONYMOUS_USER_ID);//None-Anonymous;//Optional.empty();
		}
	}
}
