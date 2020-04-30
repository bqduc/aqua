/**
 * 
 */
package net.paramount.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.session.AdminSession;

/**
 * @author ducbq
 *
 */
@WebFilter(urlPatterns = {"/*"})
public class CustomAdminFilter implements Filter {
  @Inject
  private AdminSession adminSession;

  @Inject
  private AdminConfig adminConfig;
  
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		System.out.println("Fired CustomAdminFilter!!!!");
		chain.doFilter(req, resp);
	}

}
