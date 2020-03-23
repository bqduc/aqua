/*
 * Copyright 2016-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.paramount.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import net.paramount.auth.domain.BaseACL;
import net.paramount.auth.service.AuthorizationService;
import net.paramount.config.intercept.AquaFilterInvocationSecurityMetadataSource;
import net.paramount.config.intercept.AuthenticationSuccessHandler;
import net.paramount.config.security.UrlMatchAccessDecisionManager;
import net.paramount.config.security.UrlMatchVoter;

/**
 * Spring Security Configuration.
 *
 * @author ducbq
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Inject
	private AuthenticationProvider authenticationProvider;

	@Inject
	private AccessDeniedHandler customAccessDeniedHandler;

  @Inject
  private AuthenticationSuccessHandler authenticationSuccessHandler;
  
	@Inject
	private AuthorizationService authorizationService;

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder ()/*virtualPasswordEncoder*/;
  }

	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			// rest Login
			http.antMatcher("/api/**").authorizeRequests().anyRequest().fullyAuthenticated()/*.hasRole("ADMIN")*/.and().httpBasic().and().csrf()
					.disable();
		}
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		configureAppSecurity(http);
		//configureSecurity(http);
	}
	
	private String[] buildUnauthorizedMatchers() {
		String[] unauthorizedPatterns = new String[] { 
				"/protected/**", 
				"/*", 
				"/public/**", 
				"/resources/**", 
				"/includes/**", 
				"/pages/**", 
				"/auth/register/**",
				"/login.xhtml", 
				"/javax.faces.resource/**"
		};
		return unauthorizedPatterns;
	}

	/*
	protected void configureSecurity(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
    	.authorizeRequests().antMatchers(buildUnauthorizedMatchers()).permitAll()
    	.and()
    	.authorizeRequests()
			.antMatchers(BaseACL.ADMINISTRATOR.getAntMatcher()).hasRole(BaseACL.ADMINISTRATOR.getAuthority())
			.antMatchers(BaseACL.COORDINATOR.getAntMatcher()).hasRole(BaseACL.COORDINATOR.getAuthority())
			.antMatchers(BaseACL.SUBSCRIBER.getAntMatcher()).hasRole(BaseACL.SUBSCRIBER.getAuthority())
			.antMatchers(BaseACL.MANAGER.getAntMatcher()).hasRole(BaseACL.MANAGER.getAuthority())
			.antMatchers(BaseACL.OSX.getAntMatcher()).hasRole(BaseACL.OSX.getAuthority())
			.antMatchers(BaseACL.CRSX.getAntMatcher()).hasRole(BaseACL.CRSX.getAuthority())
    	.anyRequest().authenticated()
			.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
				public <O extends FilterSecurityInterceptor> O postProcess(
					O fsi) {

//					// 覆盖SecurityMetadataSource
//					fsi.setSecurityMetadataSource(fsi.getSecurityMetadataSource());

//					// 覆盖AccessDecisionManager
					//fsi.setAccessDecisionManager(getAccessDecisionManager());

					// 增加投票项
					AccessDecisionManager accessDecisionManager = fsi.getAccessDecisionManager();
					if (accessDecisionManager instanceof AbstractAccessDecisionManager) {
						((AbstractAccessDecisionManager) accessDecisionManager).getDecisionVoters().add(new UrlMatchVoter(authorizationService));
					}

					return fsi;
				}
			})
			.and()
      .exceptionHandling().accessDeniedHandler(this.customAccessDeniedHandler)
      ;
		
      //.exceptionHandling().accessDeniedPage("/unauthorized.jsf")
      ;
    // login
    httpSecurity.formLogin()
    	.loginPage("/login.xhtml")
    	//.successHandler(this.authenticationSuccessHandler)
    	.permitAll()
      .failureUrl("/login.xhtml?authfailed=true");

    // logout
    httpSecurity
    .logout()
    .invalidateHttpSession(true)
    .deleteCookies("JSESSIONID")
    .logoutUrl("/j_spring_security_logout")
    .logoutSuccessUrl("/index.jsf")
    ;

    // not needed as JSF 2.2 is implicitly protected against CSRF
    httpSecurity.csrf().disable();
	}
	*/
  
	@Bean
	public FilterInvocationSecurityMetadataSource customSecurityMetadataSource() {
		AquaFilterInvocationSecurityMetadataSource securityMetadataSource = new AquaFilterInvocationSecurityMetadataSource();
		return securityMetadataSource;
	}

	/*@Bean
	public AccessDecisionManager customAccessDecisionManager() {
		return new AquaAccessDecisionManager();
	}*/

	/**
	 *
	 * @Description: 自定义授权AccessDecisionManager
	 *
	 * AffirmativeBased(spring security默认使用):
	 * 		只要有投通过（ACCESS_GRANTED）票，则直接判为通过。
	 * 		如果没有投通过票且反对（ACCESS_DENIED）票在1个及其以上的，则直接判为不通过。
	 * ConsensusBased(少数服从多数):
	 * 		通过的票数大于反对的票数则判为通过;通过的票数小于反对的票数则判为不通过;
	 * 		通过的票数和反对的票数相等，则可根据配置allowIfEqualGrantedDeniedDecisions
	 * 	   （默认为true）进行判断是否通过。
	 * UnanimousBased(反对票优先):
	 * 		无论多少投票者投了多少通过（ACCESS_GRANTED）票，只要有反对票（ACCESS_DENIED），
	 * 		那都判为不通过;如果没有反对票且有投票者投了通过票，那么就判为通过.
	 *
	 *
	 * @auther: csp
	 * @date:  2019/1/7 下午9:12
	 * @return: org.springframework.security.access.AccessDecisionManager
	 *
	 */
	@Bean
	public AccessDecisionManager getAccessDecisionManager() {
		return new UrlMatchAccessDecisionManager();
	}
	
	
  protected void configureAppSecurity(HttpSecurity http) throws Exception {
  	http
   		.csrf().disable()
      .authorizeRequests()
       	.antMatchers(buildUnauthorizedMatchers()).permitAll()
       	.anyRequest().access("@authorizationChecker.check(request, authentication)")
      .and()
      	.formLogin()
       	.successHandler(this.authenticationSuccessHandler)
        .loginPage("/login.xhtml")
        .permitAll()
      .and()
      	.logout()
       		.logoutUrl("/j_spring_security_logout")
       		.logoutSuccessUrl("/index.jsf")
       		.invalidateHttpSession(true)
       		.deleteCookies("JSESSIONID")
          .permitAll()
      .and()
      	.exceptionHandling().accessDeniedHandler(this.customAccessDeniedHandler);
  }
}
