package keepinchecker.utility;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import keepinchecker.setup.KeepInCheckerTestCase;

public class SecurityUtilitiesTest extends KeepInCheckerTestCase {
	
	@Test
	public void testEncryptDecrypt() throws Exception {
		String plaintext = "This is plaintext. 1234567890)(*&^%$#:@!|}{><\\'";
		byte[] encryptedTextBytes = SecurityUtilities.encrypt(plaintext.getBytes(StandardCharsets.UTF_8));
		byte[] decryptedTextBytes = SecurityUtilities.decrypt(encryptedTextBytes);
		
		assertEquals("String should have been decrypted back to its original value",
				plaintext, new String(decryptedTextBytes, StandardCharsets.UTF_8));
	}
	
}
