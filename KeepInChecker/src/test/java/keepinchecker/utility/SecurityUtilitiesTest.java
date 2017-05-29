package keepinchecker.utility;

import static org.junit.Assert.*;

import org.junit.Test;

public class SecurityUtilitiesTest {
	
	@Test
	public void testEncryptDecrypt() throws Exception {
		String plaintext = "This is plaintext.";
		byte[] encryptedTextBytes = SecurityUtilities.encrypt(plaintext);
		
		String decryptedPlaintext = SecurityUtilities.decrypt(encryptedTextBytes);
		assertEquals("String should have been decrypted back to its original value",
				"This is plaintext.", decryptedPlaintext);
	}

}
