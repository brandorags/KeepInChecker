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


package keepinchecker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
	 * @throws Exception
	 */
	public DbSession(String dbPath) throws Exception {
		Class.forName("org.sqlite.JDBC");		
		
		connection = DriverManager.getConnection(dbPath);
		connection.setAutoCommit(false);
		
		statement = connection.createStatement();
	}
	
	public void commit() throws SQLException {
		statement.close();
		connection.commit();
	}
	
	public void close() throws SQLException {
		connection.close();
	}
	
	public void commitAndClose() throws SQLException {
		statement.close();
		connection.commit();
		connection.close();
	}
	
	public ResultSet find(String sql) throws SQLException {
		return statement.executeQuery(sql);
	}
	
	public void execute(String sql) throws SQLException {
		statement.executeUpdate(sql);
	}
	
	public void createTablesIfNoneExist() throws SQLException {
		StringBuilder builder = new StringBuilder();

		builder.append("CREATE TABLE IF NOT EXISTS \"Packet\" (");
		builder.append("`PacketId` INTEGER NOT NULL, ");
		builder.append("`DateReceived` INTEGER NOT NULL, ");
		builder.append("`Timezone` TEXT, ");
		builder.append("`Get` TEXT, ");
		builder.append("`Host` TEXT, ");
		builder.append("`Referer` TEXT, ");
		builder.append("PRIMARY KEY(PacketId))");
		statement.executeUpdate(builder.toString());
		
		builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS \"User\" (");
		builder.append("`UserId` INTEGER NOT NULL, ");
		builder.append("`UserName` TEXT NOT NULL, ");
		builder.append("`UserEmail` TEXT NOT NULL, ");
		builder.append("`UserEmailPassword` TEXT NOT NULL, ");
		builder.append("`PartnerEmails` TEXT NOT NULL, ");
		builder.append("`EmailFrequency` TEXT NOT NULL, ");
		builder.append("`EmailLastSentDate` INTEGER, ");
		builder.append("PRIMARY KEY(UserId))");
		statement.executeUpdate(builder.toString());
	}

}
