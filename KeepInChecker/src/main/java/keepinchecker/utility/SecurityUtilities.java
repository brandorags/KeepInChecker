package keepinchecker.utility;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtilities {
	
	// NOTE: this is not the key that is used in production
	private static final byte[] KEY = "6fpzZk+/o9pHDIM8".getBytes(StandardCharsets.UTF_8);
	private static final String ALGORITHM_TYPE = "AES";
	
	public static byte[] encrypt(String value) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(KEY, ALGORITHM_TYPE);
		Cipher cipher = Cipher.getInstance(ALGORITHM_TYPE);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		
		return cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
	}
	
	public static String decrypt(byte[] value) throws Exception {
		SecretKeySpec secretKeySpec = new SecretKeySpec(KEY, ALGORITHM_TYPE);
		Cipher cipher = Cipher.getInstance(ALGORITHM_TYPE);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		
		return new String(cipher.doFinal(value));
	}

}
