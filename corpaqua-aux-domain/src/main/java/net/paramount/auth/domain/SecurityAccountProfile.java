/**
 * 
 */
package net.paramount.auth.domain;

import lombok.Builder;
import net.paramount.auth.entity.UserAccount;
import net.paramount.domain.DtoCore;

/**
 * @author ducbq
 *
 */
@Builder
public class SecurityAccountProfile extends DtoCore {
	private static final long serialVersionUID = -1051763928685608384L;

	public static final String ANONYMOUS_USER = "anonymousUser";

	private UserAccount userAccount;

	@Builder.Default
	private String displayName = ANONYMOUS_USER;

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getDisplayName() {
		if (null != this.userAccount)
			return this.userAccount.getDisplayName();

		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isPresentUserAccount() {
		return (null != this.userAccount);
	}
}
