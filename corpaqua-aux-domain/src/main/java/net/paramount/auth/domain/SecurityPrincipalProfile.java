/**
 * 
 */
package net.paramount.auth.domain;

import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import net.paramount.auth.entity.Authority;
import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.common.CommonConstants;
import net.paramount.common.ListUtility;
import net.paramount.domain.model.dto.FacadeCore;

/**
 * @author ducbq
 *
 */
@Builder
public class SecurityPrincipalProfile extends FacadeCore {
	private static final long serialVersionUID = -1051763928685608384L;

	private SecurityAccountProfile userAccount;
	private Authentication authentication;

	@Builder.Default
	private List<Authority> grantedAuthorities = ListUtility.createList();

	@Builder.Default
	private String displayName = CommonConstants.ANONYMOUS_USER;

	public SecurityAccountProfile getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(SecurityAccountProfile userAccount) {
		this.userAccount = userAccount;
	}

	public String getDisplayName() {
		if (null != this.userAccount) {
			displayName = this.userAccount.getDisplayName();
		}

		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isPresentUserAccount() {
		return (null != this.userAccount);
	}

	public List<Authority> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setGrantedAuthorities(List<Authority> grantedAuthorities) {
		this.grantedAuthorities.addAll(grantedAuthorities);
	}

	public void addGrantedAuthority(Authority grantedAuthority) {
		this.grantedAuthorities.add(grantedAuthority);
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public String getDisplayRoles() {
		StringBuilder displayRoles = new StringBuilder();
		Iterator<? extends GrantedAuthority> itr = this.getGrantedAuthorities().iterator();
		Authority currentAuthority = null;
		while (itr.hasNext()) {
			currentAuthority = (Authority)itr.next();
			displayRoles.append(currentAuthority.getName());
			if (itr.hasNext()) {
				displayRoles.append(CommonConstants.SEPARATOR_SEMICOLON);
			}
		}
		return displayRoles.toString();
	}
}
