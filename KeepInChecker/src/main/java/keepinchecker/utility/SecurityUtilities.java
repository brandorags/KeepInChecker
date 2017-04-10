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
