package keepinchecker.setup;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import keepinchecker.constants.Constants;
import keepinchecker.database.DbSession;

public class KeepInCheckerTestCase {	
	
	@BeforeClass
	public static void init() {
		Constants.DATABASE_PATH = "jdbc:sqlite:test.sqlite";
		
		try {
			DbSession session = new DbSession(Constants.DATABASE_PATH);
			session.createTablesIfNoneExist();
			session.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void cleanUp() {
		File database = new File(StringUtils.substringAfterLast(Constants.DATABASE_PATH, ":"));
		database.delete();
	}
	
}
