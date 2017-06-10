package keepinchecker.utility;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import keepinchecker.database.KeepInCheckerPacketManager;
import keepinchecker.database.entity.KeepInCheckerPacket;
import keepinchecker.setup.KeepInCheckerTestCase;

public class SecurityUtilitiesTest extends KeepInCheckerTestCase {
	
	@Test
	public void testEncryptDecrypt() throws Exception {
		String plaintext = "This is plaintext.";
		byte[] encryptedTextBytes = SecurityUtilities.encrypt(plaintext);
		
		String decryptedPlaintext = SecurityUtilities.decrypt(encryptedTextBytes);
		assertEquals("String should have been decrypted back to its original value",
				"This is plaintext.", decryptedPlaintext);
	}
	
	@Test
	public void testSaveEncryptedValuesToDatabase() throws Exception {
		String getValue = "We have a Get value here 182365988dfkb!@#$%^&*()";
		String hostValue = "Host is the value for this one ,.?\"\\\\}{~`+";
		String refererValue = "And Referer is *&%%^XVDJWxch45678*&><>?|+= the value on this!";
		byte[] encryptedTextBytesGet = SecurityUtilities.encrypt(getValue);
		byte[] encryptedTextBytesHost = SecurityUtilities.encrypt(hostValue);
		byte[] encryptedTextBytesReferer = SecurityUtilities.encrypt(refererValue);
		
		KeepInCheckerPacketManager packetManager = new KeepInCheckerPacketManager();
		KeepInCheckerPacket packet = new KeepInCheckerPacket();
		packet.setTimestamp(1);
		packet.setGetValue(encryptedTextBytesGet);
		packet.setHostValue(encryptedTextBytesHost);
		packet.setRefererValue(encryptedTextBytesReferer);
		packetManager.savePackets(Arrays.asList(packet));
		
		KeepInCheckerPacket packetFromDb = packetManager.getPackets().get(0);
		String decryptedTextStringGet = SecurityUtilities.decrypt(packetFromDb.getGetValue());
		String decryptedTextStringHost = SecurityUtilities.decrypt(packetFromDb.getHostValue());
		String decryptedTextStringReferer = SecurityUtilities.decrypt(packetFromDb.getRefererValue());
		
		assertEquals("Get value should have been successfully decrypted from the database", 
				getValue, decryptedTextStringGet);
		assertEquals("Host value should have been successfully decrypted from the database", 
				hostValue, decryptedTextStringHost);
		assertEquals("Referer value should have been successfully decrypted from the database", 
				refererValue, decryptedTextStringReferer);
	}

}
