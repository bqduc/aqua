/**
 * 
 */
package net.paramount.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import net.paramount.auth.comp.JsonWebTokenService;
import net.paramount.framework.entity.auth.AuthenticationDetails;

/**
 * @author ducbq
 *
 */
public class JwtTokenFilter extends GenericFilterBean {
	private JsonWebTokenService jwtTokenProvider;

	public JwtTokenFilter(JsonWebTokenService jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

		Authentication authentication = null;
		AuthenticationDetails authenticationDetails = null;
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
		if (token != null && jwtTokenProvider.validateToken(token)) {
			authenticationDetails = jwtTokenProvider.generateAuthenticationDetails(token);
			authentication = new UsernamePasswordAuthenticationToken(authenticationDetails.getSsoId(), authenticationDetails.getPassword(), authenticationDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		try {
			filterChain.doFilter(req, res);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
