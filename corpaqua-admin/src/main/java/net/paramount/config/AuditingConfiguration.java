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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import net.paramount.auth.domain.SecurityPrincipalProfile;
import net.paramount.auth.service.AuthorizationService;

/**
 * @author ducbq
 *
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {
	@Inject 
	private AuthorizationService authorizationService;

	/*@Bean
	public AuditorAware<String> auditorProviderByKey() {
		return new AuditorAwareImpl();
	}*/

	@Bean
	public AuditorAware<Long> auditorProvider() {
		return new AuditorAwareByKeyImpl();
	}

	class AuditorAwareImpl implements AuditorAware<String> {
		@Override
		public Optional<String> getCurrentAuditor() {
			Authentication auth = null;
			SecurityPrincipalProfile securityPrincipalProfile = authorizationService.getActiveSecuredProfile();
			if (null != securityPrincipalProfile) {
				auth = securityPrincipalProfile.getAuthentication();
			}

			if (auth==null)
				return Optional.of("NOANNO");//None-Anonymous;//Optional.empty();

			if (auth.getPrincipal() instanceof String) {
				return Optional.of((String)auth.getPrincipal());
			}

			User authUser = (User)auth.getPrincipal();
			return Optional.of(authUser.getUsername());
			/*
			Optional<User> optLoggedInUser = this.getCurrentLoggedInUser();
			if (optLoggedInUser.isPresent())
				return Optional.of(optLoggedInUser.get().getUsername());

			return Optional.of("ANNO");//Annonymous
			*/
		}
	}

	class AuditorAwareByKeyImpl implements AuditorAware<Long> {
		@Override
		public Optional<Long> getCurrentAuditor() {
			SecurityPrincipalProfile securityPrincipalProfile = null;
			try {
				securityPrincipalProfile = authorizationService.getActiveSecuredProfile();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (null != securityPrincipalProfile && null != securityPrincipalProfile.getUserAccount()) {
				return Optional.of(securityPrincipalProfile.getUserAccount().getId());
			}

			return Optional.of(-1l);//None-Anonymous;//Optional.empty();
		}
	}
}
