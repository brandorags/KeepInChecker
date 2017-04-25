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


package keepinchecker.main;

import keepinchecker.constants.Constants;
import keepinchecker.database.DbSession;
import keepinchecker.database.Queries;
import keepinchecker.database.entity.User;
import keepinchecker.gui.KeepInCheckerSystemTray;
import keepinchecker.network.PacketSniffer;

public class KeepInChecker {
	
	public static void main(String[] args) throws Exception {
		initializeDatabaseConnection();
		initializeUser();
		recordNetworkTraffic();
		launchSystemTray();
	}
	
	private static void initializeDatabaseConnection() throws Exception {
		DbSession dbSession = new DbSession(Constants.DATABASE_PATH);
		dbSession.createTablesIfNoneExist();
		dbSession.commitAndClose();
	}
	
	private static void initializeUser() throws Exception {
		User user = Queries.getUser();
		if (user != null) {
			Constants.USER = user;
		}
	}
	
	private static void recordNetworkTraffic() {
		Thread snifferThread = new Thread(new PacketSniffer());
		snifferThread.start();
	}
	
	private static void launchSystemTray() {
		KeepInCheckerSystemTray systemTray = new KeepInCheckerSystemTray();
		systemTray.run();
	}

}
