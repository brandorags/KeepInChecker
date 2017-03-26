package keepinchecker.main;

import java.io.File;
import java.net.InetAddress;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

import com.sun.javafx.PlatformUtil;

import keepinchecker.contstants.Contstants;
import keepinchecker.database.DbSession;
import keepinchecker.utility.SecurityUtilities;

public class KeepInChecker {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Hello, world!");
		initializeDatabaseConnection();
		
		// delete me!
//		InetAddress address = InetAddress.getByName("192.168.0.6");
//		PcapNetworkInterface networkInterface = Pcaps.getDevByAddress(address);
//		System.out.println(networkInterface.getName());
//		int snapLen = 65536;
//		PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
//		int timeout = 5000;
//		PcapHandle handle = networkInterface.openLive(snapLen, mode, timeout);
//		Packet packet = handle.getNextPacketEx();
//		handle.close();
//		System.out.println(packet.getPayload());

		byte[] value = SecurityUtilities.encrypt("I need to be encrypted, please!");
		System.out.println(new String(value));
		String decryptedValue = SecurityUtilities.decrypt(value);
		System.out.println(decryptedValue);
		
		value = SecurityUtilities.encrypt(decryptedValue);
		System.out.println(new String(value));
		decryptedValue = SecurityUtilities.decrypt(value);
		System.out.println(decryptedValue);
	}
	
	private static void initializeDatabaseConnection() throws Exception {
		Contstants.databasePath = getDatabasePath();
		DbSession dbSession = new DbSession(Contstants.databasePath, false);
		dbSession.createTablesIfNoneExist();
		dbSession.commit();
		dbSession.close();
	}
	
	private static String getDatabasePath() throws Exception {
		String databasePath = "";

		if (PlatformUtil.isMac() || PlatformUtil.isLinux()) {
			databasePath = "jdbc:sqlite:/usr/local/.KeepInChecker/KeepInChecker.sqlite";
			if (!(new File("/usr/local/.KeepInChecker").exists())) {
				File keepInCheckerDir = new File("/usr/local/.KeepInChecker");
				boolean dirCreationSuccess = keepInCheckerDir.mkdirs();
				if (!dirCreationSuccess) {
					throw new Exception("Couldn't create directory. This application must be run by sudo.");
				}
			}
		} else if (PlatformUtil.isWindows()) {
			databasePath = "jdbc:sqlite:C:\\KeepInChecker\\KeepInChecker.sqlite";
			if (!(new File("C:\\KeepInChecker").exists())) {
				File keepInCheckerDir = new File("C:\\KeepInChecker");
				keepInCheckerDir.mkdirs();
				boolean dirCreationSuccess = keepInCheckerDir.mkdirs();
				if (!dirCreationSuccess) {
					throw new Exception("Couldn't create directory. This application must be run by Administrator.");					
				}
			}
		}
		
		return databasePath;
	}

}
