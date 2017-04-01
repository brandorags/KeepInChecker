package keepinchecker.main;

import keepinchecker.constants.Constants;
import keepinchecker.database.DbSession;
import keepinchecker.gui.SettingsDialog;

public class KeepInChecker {
	
	public static void main(String[] args) throws Exception {
		initializeDatabaseConnection();
		SettingsDialog dialog = new SettingsDialog();
		dialog.open();
	}
	
	private static void initializeDatabaseConnection() throws Exception {
		DbSession dbSession = new DbSession(Constants.DATABASE_PATH, false);
		dbSession.createTablesIfNoneExist();
		dbSession.commit();
		dbSession.close();
	}

}
