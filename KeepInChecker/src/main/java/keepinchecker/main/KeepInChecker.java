package keepinchecker.main;

import java.io.File;

import com.sun.javafx.PlatformUtil;

import keepinchecker.contstants.Contstants;
import keepinchecker.database.DbSession;

public class KeepInChecker {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Hello, world!");
		initializeDatabaseConnection();
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
