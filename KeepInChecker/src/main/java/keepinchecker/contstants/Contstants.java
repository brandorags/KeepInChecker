package keepinchecker.contstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javafx.PlatformUtil;

public class Contstants {
	
	public static final List<String> OBJECTIONABLE_WORDS = new ArrayList<>(Arrays.asList("stackoverflow", "reddit",
			"imgur", "awesome", "the"));
	
	public static final List<String> PACKET_KEYWORDS = new ArrayList<>(Arrays.asList("GET", "Host", "Referer"));;
	
	public static final Map<String, String> THIRD_PARTY_LIBRARIES_MAP = new HashMap<>();
	
	public static Object CURRENT_USER = null;

	public static String DATABASE_PATH;
	
	static {
		try {			
			DATABASE_PATH = getDatabasePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
