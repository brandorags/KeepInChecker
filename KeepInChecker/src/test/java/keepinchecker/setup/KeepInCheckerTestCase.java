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
