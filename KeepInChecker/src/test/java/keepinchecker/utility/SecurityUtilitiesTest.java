/** 
 * Copyright 2017 Brandon Ragsdale 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *  
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */


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
