/**
 * 
 */
package net.paramount.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.paramount.servlets.InventoryImageServlet;
import net.paramount.servlets.ServletConstants;
import net.paramount.servlets.UserProfileImageServlet;

/**
 * @author bqduc
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Configuration
public class ServletsConfiguration {

	@Bean
	public ServletRegistrationBean delegateInventoryImageServlet() {
		return new ServletRegistrationBean(new InventoryImageServlet(), ServletConstants.inventoryImageUrlMapping);
	}

	@Bean
	public ServletRegistrationBean<UserProfileImageServlet> delegateUserProfileImageServlet() {
		return new ServletRegistrationBean(new UserProfileImageServlet(), ServletConstants.userProfileImageUrlMapping);
	}
}