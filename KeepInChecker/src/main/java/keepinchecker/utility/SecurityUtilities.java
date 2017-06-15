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
import java.security.GeneralSecurityException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SecurityUtilities {
	
	private static final byte[] KEY;
	private static final byte[] IV;
	private static final String ALGORITHM_TYPE = "AES/CBC/PKCS5Padding";
	
	static {
		// NOTE: this is not the key that is used in production
		String keyString = ")E3x_+z$Emen,hjgRc]>*W4{g)p]<DPt"; // 32 chars
		KEY = keyString.getBytes(StandardCharsets.UTF_8);
		// NOTE: this is not the initialization vector that is used in production
		String ivString = "B,~j2Vw3W8en_Mf~"; // 16 chars
		IV = ivString.getBytes(StandardCharsets.UTF_8);
	}

	public static byte[] encrypt(byte[] value) throws GeneralSecurityException {
		Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
		return cipher.doFinal(value);
	}
	
	public static byte[] decrypt(byte[] value) throws GeneralSecurityException {
		Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
		return cipher.doFinal(value);
	}
	
	private static Cipher initCipher(int cryptMode) throws GeneralSecurityException {
		Security.insertProviderAt(new BouncyCastleProvider(), 1);
		
		SecretKeySpec key = new SecretKeySpec(KEY, ALGORITHM_TYPE);
		IvParameterSpec iv = new IvParameterSpec(IV);
		Cipher cipher = Cipher.getInstance(ALGORITHM_TYPE);
		cipher.init(cryptMode, key, iv);
		
		return cipher;
	}

}
