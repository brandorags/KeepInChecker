package keepinchecker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provides a layer of abstraction
 * for when performing database transactions.
 */
public class DbSession {
	
	private Connection connection;
	private Statement statement;
	
	/**
	 * Constructor for DbSession
	 * 
	 * @param dbPath - the path to the database
	 * @param autoCommit - determines whether
	 * or not to auto commit the database transaction
	 * @throws Exception
	 */
	public DbSession(String dbPath, boolean autoCommit) throws Exception {
		Class.forName("org.sqlite.JDBC");
		
		connection = DriverManager.getConnection(dbPath);
		connection.setAutoCommit(autoCommit);
		
		statement = connection.createStatement();
	}
	
	public void commit() throws SQLException {
		statement.close();
		connection.commit();
	}
	
	public void close() throws SQLException {
		connection.close();
	}
	
	public void createTablesIfNoneExist() throws SQLException {
		StringBuilder builder = new StringBuilder();

		builder.append("CREATE TABLE IF NOT EXISTS \"Packet\" (");
		builder.append("`PacketId` INTEGER NOT NULL,");
		builder.append("`DateReceived` TEXT NOT NULL,");
		builder.append("`Timezone` TEXT,");
		builder.append("`Get` TEXT,");
		builder.append("`Host` TEXT,");
		builder.append("`Referrer` TEXT,");
		builder.append("PRIMARY KEY(PacketId))");
		statement.executeUpdate(builder.toString());
		
		builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS \"User\" (");
		builder.append("`UserId` INTEGER NOT NULL,");
		builder.append("`UserName` TEXT NOT NULL,");
		builder.append("`UserEmail` TEXT NOT NULL,");
		builder.append("`UserEmailPassword` TEXT NOT NULL,");
		builder.append("`PartnerEmails` TEXT NOT NULL,");
		builder.append("`EmailFrequency` TEXT NOT NULL,");
		builder.append("`EmailLastSentDate` REAL,");
		builder.append("PRIMARY KEY(UserId))");
		statement.executeUpdate(builder.toString());
	}

}
