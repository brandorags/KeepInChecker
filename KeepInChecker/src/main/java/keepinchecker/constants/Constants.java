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


package keepinchecker.constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javafx.PlatformUtil;

import keepinchecker.database.entity.User;

public class Constants {
	
	public static final List<String> OBJECTIONABLE_WORDS = new ArrayList<>(Arrays.asList("stackoverflow", "reddit",
			"imgur", "awesome", "the"));
	
	public static final List<String> PACKET_KEYWORDS = new ArrayList<>(Arrays.asList("GET", "Host", "Referer"));;
	
	public static final Map<String, String> THIRD_PARTY_LIBRARIES_MAP = new HashMap<>();
	
	public static User USER = null;
	
	public static final String USER_EMAIL_FREQUENCY_DAILY = "Daily";
	
	public static final String USER_EMAIL_FREQUENCY_WEEKLY = "Weekly";

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
			databasePath = "jdbc:sqlite:C:\\Program Files\\KeepInChecker\\KeepInChecker.sqlite";
			if (!(new File("C:\\Program Files\\KeepInChecker").exists())) {
				File keepInCheckerDir = new File("C:\\Program Files\\KeepInChecker");
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
