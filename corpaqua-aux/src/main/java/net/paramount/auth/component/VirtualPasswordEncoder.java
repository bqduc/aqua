/**
 * 
 */
package net.paramount.auth.component;

import javax.inject.Named;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.paramount.auth.constants.AuxGlobalConstants;

/**
 * @author ducbq
 *
 */
@Named("virtualPasswordEncoder")
@Component
public class VirtualPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence plainTextPassword) {
		String hashed = BCrypt.hashpw(preConstructPassword(plainTextPassword.toString()), BCrypt.gensalt(12));
		return hashed;
	}

	@Override
	public boolean matches(CharSequence plainTextPassword, String encodedPassword) {
		String upgradedPassword = this.encode(plainTextPassword);
		return BCrypt.checkpw(upgradedPassword, encodedPassword);
	}

	private String preConstructPassword(String rawPassword) {
		return new StringBuilder(rawPassword).append(AuxGlobalConstants._SALT_EXTENDED).toString();
	}

}
