/**
 * 
 */
package net.paramount.autx;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.paramount.auth.comp.JsonWebTokenServiceProvider;
import net.paramount.auth.entity.SecurityAccountProfile;
import net.paramount.framework.entity.auth.AuthenticationDetails;

/**
 * @author ducbq
 *
 */
public class TestEntry {
	static String encodedBufffer = "$2a$10$0NSLJQsPyqBfSSSga5qYiuOQ3gRyee8cTH88UlT6Q0wfQ0PxiqmnK";
	static String encodedBuffferRepo = "$2a$10$0PDcmmGtXuWSAMI/iQrgBeYHrx9AvR86Zw4k9nWT7RtQVQ/bkJB2.";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testEncoding();
		testMatches();
	}
	
	protected static void testJToken() {
		JsonWebTokenServiceProvider jwtTokenProvider = new JsonWebTokenServiceProvider();

		AuthenticationDetails userDetails = new SecurityAccountProfile();
		userDetails.setId(Long.valueOf(10292019));
		userDetails.setSsoId("bdq1hc");
		//String token = jwtTokenProvider.generateToken(userDetails);
		//System.out.println(token);

		AuthenticationDetails unmarshalledUserDetails = jwtTokenProvider.generateAuthenticationDetails("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDI5MjAxOSNiZHExaGMiLCJpYXQiOjE1ODMxNTYzMTYsImV4cCI6MTU4Mzc2MTExNn0.dRsYSBfXIKIxqzml462gA8me3FCzZNz77ZPMdAeuy4hznxZODJOjI1ssd0YW4bRsjA3_D5nCBJkR2Bgp1GAM-Q");
		System.out.println(unmarshalledUserDetails);
	}

	protected static void testMatches() {
		PasswordEncoder encoder = getEncoder();
		boolean matched = encoder.matches("subscriber", encodedBuffferRepo);
		System.out.println(matched);
	}

	protected static void testEncoding() {
		String encoded = getEncoder().encode("subscriber");
		System.out.println(encoded);
	}

	private static PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
}
