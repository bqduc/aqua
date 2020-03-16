/**
 * 
 */
package net.paramount.auth.domain;

/**
 * @author ducbq
 *
 */
public enum BasicRole {
	ADMINISTRATOR ("corpadmin"), 
	MANAGER ("corpmanager"), 
	COORDINATOR ("coordinator"), 
	SUBSCRIBER ("corpsubscriber");
	
	private BasicRole(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	private String user;
}
