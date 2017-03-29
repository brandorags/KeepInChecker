package keepinchecker.main;

import keepinchecker.contstants.Contstants;
import keepinchecker.database.DbSession;

public class KeepInChecker {
	
	public static void main(String[] args) throws Exception {
		initializeDatabaseConnection();
	}
	
	private static void initializeDatabaseConnection() throws Exception {
		DbSession dbSession = new DbSession(Contstants.DATABASE_PATH, false);
		dbSession.createTablesIfNoneExist();
		dbSession.commit();
		dbSession.close();
	}

}
