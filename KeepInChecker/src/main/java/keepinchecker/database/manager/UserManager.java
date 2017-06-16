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


package keepinchecker.database.manager;

import java.util.ArrayList;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.User;
import keepinchecker.utility.SecurityUtilities;

public class UserManager {
	
	private ConnectionSource connectionSource;
	private Dao<User, Integer> userDao;
	
	public void saveUser(User user) throws Exception {
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_PATH);
			userDao = DaoManager.createDao(connectionSource, User.class);
			
			encryptSettings(user);
			
			userDao.createOrUpdate(user);	
		} finally {
			connectionSource.close();
		}
		
		// update the cached user
		Constants.USER = getUser();
	}

	public User getUser() throws Exception {
		User user = null;
		
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_PATH);
			userDao = DaoManager.createDao(connectionSource, User.class);
						
			// we should only ever have one user
			user = userDao.queryForId(1);
			
			if (user != null) {				
				decryptSettings(user);
			}
		} finally {
			connectionSource.close();
		}
		
		return user;
	}
	
	private void encryptSettings(User user) throws Exception {
		user.setUserName(SecurityUtilities.encrypt(user.getUserName()));
		user.setUserEmail(SecurityUtilities.encrypt(user.getUserEmail()));
		user.setUserEmailPassword(SecurityUtilities.encrypt(user.getUserEmailPassword()));
		
		ArrayList<byte[]> encryptedPartnerEmails = new ArrayList<>();
		for (byte[] partnerEmail : user.getPartnerEmails()) {
			encryptedPartnerEmails.add(SecurityUtilities.encrypt(partnerEmail));
		}
		
		// clear out the existing partner emails
		// and add the new encrypted ones
		user.getPartnerEmails().clear();
		user.setPartnerEmails(encryptedPartnerEmails);
	}
	
	private void decryptSettings(User user) throws Exception {
		user.setUserName(SecurityUtilities.decrypt(user.getUserName()));
		user.setUserEmail(SecurityUtilities.decrypt(user.getUserEmail()));
		user.setUserEmailPassword(SecurityUtilities.decrypt(user.getUserEmailPassword()));
		
		ArrayList<byte[]> decryptedPartnerEmails = new ArrayList<>();
		for (byte[] partnerEmail : user.getPartnerEmails()) {
			decryptedPartnerEmails.add(SecurityUtilities.decrypt(partnerEmail));
		}
		
		// clear out the existing partner emails
		// and add the new decrypted ones
		user.getPartnerEmails().clear();
		user.setPartnerEmails(decryptedPartnerEmails);
	}

}
