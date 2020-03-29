/**
 * 
 */
package net.paramount.exceptions;

/**
 * @author ducbq
 *
 */
public enum AuthenticationCode {
	ERROR_UNKNOWN ("aux.invalid.unknown"),
	ERROR_INVALID_PRINCIPAL ("aux.invalid.principal"),
	ERROR_INVALID_CREDENTIAL ("aux.invalid.credential"),
	ERROR_INVALID_PERMISSION ("aux.invalid.permission"),
	ERROR_INVALID_PROFILE ("aux.invalid.profile"), 
	ERROR_PROFILE_INACTIVATE ("aux.inactive.profile"),
	;

	private String message;
	
	private AuthenticationCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
