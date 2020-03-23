/**
 * 
 */
package net.paramount.auth.domain;

/**
 * @author ducbq
 *
 */
public enum BaseACL {
	ADMINISTRATOR ("/spaces/administration/**", "administration", "administrator", "administrator@ecosphere.net", "Admin", "Nguyễn Trần"), 
	MANAGER ("/spaces/management/**", "management", "manager", "manager@ecosphere.net", "Lê Văn", "Manager"), 
	COORDINATOR ("/spaces/coordination/**", "coordination", "coordinator", "coordination@ecosphere.net", "Coordinator", "Hồ Hoàng"), 
	SUBSCRIBER ("/spaces/subscription/**", "subscription", "subscriber", "subscriber@ecosphere.net", "Subscriber", "Ngô Thị"),
	OSX ("/spaces/osx/**", "osx", "osxer", "osxer@ecosphere.net", "Osxer", "Thái Tông"),
	CRSX ("/spaces/crsx/**", "crsx", "crsxer", "crsxer@ecosphere.net", "Crsxer", "Phùng Hổ"),
	;

	private String antMatcher;
	private String authority;
	private String user;
	private String email;
	private String firstName;
	private String lastName;

	private BaseACL(String antMatcher, String role, String user, String email, String firstName, String lastName) {
		this.antMatcher = antMatcher;
		this.authority = role;
		this.user = user;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUser() {
		return user;
	}

	public String getAuthority() {
		return authority;
	}

	public String getAntMatcher() {
		return antMatcher;
	}

	public static boolean exists(String user) {
		for (BaseACL acl :BaseACL.values()) {
			if (acl.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}

	public static BaseACL parse(String user) {
		for (BaseACL acl :BaseACL.values()) {
			if (acl.getUser().equals(user)) {
				return acl;
			}
		}
		return null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
