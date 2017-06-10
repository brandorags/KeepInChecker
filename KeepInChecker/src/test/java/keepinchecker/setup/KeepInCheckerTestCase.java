package keepinchecker.setup;

import java.io.File;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.KeepInCheckerPacket;
import keepinchecker.database.entity.User;

public class KeepInCheckerTestCase {	
	
	@BeforeClass
	public static void init() {
		Constants.DATABASE_PATH = "jdbc:sqlite:test.sqlite";
		createTables(Constants.DATABASE_PATH);
	}
	
	@AfterClass
	public static void cleanUp() {
		File database = new File(StringUtils.substringAfterLast(Constants.DATABASE_PATH, ":"));
		database.delete();
	}
	
	private static void createTables(String databasePath) {
		ConnectionSource connectionSource;
		try {
			connectionSource = new JdbcConnectionSource(databasePath);
			TableUtils.createTableIfNotExists(connectionSource, User.class);
			TableUtils.createTableIfNotExists(connectionSource, KeepInCheckerPacket.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
