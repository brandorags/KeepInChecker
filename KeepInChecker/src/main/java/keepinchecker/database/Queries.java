package keepinchecker.database;

import java.sql.ResultSet;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.User;

public class Queries {
	
	/**
	 * Inserts a new user's personal data, such as the username,
	 * email address, email password, etc., into the database.
	 * 
	 * @param user - the user to save to the database 
	 * @throws Exception 
	 */
	public static void saveUser(User user) throws Exception {
		if (Constants.USER != null) {
			updateUser(Constants.USER, user);
			return;
		}
		
		DbSession session = new DbSession(Constants.DATABASE_PATH);
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO User (UserName, UserEmail, UserEmailPassword, PartnerEmails, EmailFrequency) ");
		sql.append("VALUES('" + user.getUserName() + "', ");
		sql.append("'" + user.getUserEmail() + "', ");
		sql.append("'" + user.getUserEmailPassword() + "', ");
		sql.append("'" + String.join(",", user.getPartnerEmails()) + "', ");
		sql.append("'" + user.getEmailFrequency() + "')");
		
		session.execute(sql.toString());
		session.commitAndClose();
		
		// initialize user constant
		Constants.USER = getUser();
	}
	
	public static void updateUser(User currentUser, User updatedUser) throws Exception {
		DbSession session = new DbSession(Constants.DATABASE_PATH);
		StringBuilder sql = new StringBuilder();
		StringBuilder updateValues = new StringBuilder();
		boolean needsUpdate = false;
		
		if (!StringUtils.equals(currentUser.getUserName(), updatedUser.getUserName())) {
			updateValues.append("UserName = '" + updatedUser.getUserName() + "', ");
			needsUpdate = true;
		}
		
		if (!StringUtils.equals(currentUser.getUserEmail(), updatedUser.getUserEmail())) {
			updateValues.append("UserEmail = '" + updatedUser.getUserEmail() + "', ");
			needsUpdate = true;
		}

		if (!StringUtils.equals(currentUser.getUserEmailPassword(), updatedUser.getUserEmailPassword())) {
			updateValues.append("UserEmailPassword = '" + updatedUser.getUserEmailPassword() + "', ");
			needsUpdate = true;
		}
		
		if (!currentUser.getPartnerEmails().containsAll(updatedUser.getPartnerEmails())) {
			updateValues.append("PartnerEmails = '" + String.join(",", updatedUser.getUserEmailPassword()) + "', ");
			needsUpdate = true;
		}
		
		if (!StringUtils.equals(currentUser.getEmailFrequency(), updatedUser.getEmailFrequency())) {
			updateValues.append("EmailFrequency = '" + updatedUser.getEmailFrequency() + "'");
			needsUpdate = true;
		}
		
		if (needsUpdate) {
			sql.append("UPDATE User SET ");
			// remove any trailing commas
			if (updateValues.toString().trim().charAt(updateValues.toString().trim().length() - 1) == ',') {
				sql.append(StringUtils.chop(updateValues.toString().trim()));
			} else {				
				sql.append(updateValues.toString());
			}
			sql.append(" WHERE UserId = 1");
			
			session.execute(sql.toString());
			session.commitAndClose();
			
			Constants.USER = getUser();
		}
	}

	public static User getUser() throws Exception {
		User user = null;
		
		DbSession session = new DbSession(Constants.DATABASE_PATH);
		ResultSet set = session.find("SELECT * FROM User LIMIT 1");
		if (set.next()) {
			user = new User();
			
			user.setUserName(set.getString("UserName"));
			user.setUserEmail(set.getString("UserEmail"));
			user.setUserEmailPassword(set.getString("UserEmailPassword"));
			user.setPartnerEmails(Arrays.asList(set.getString("PartnerEmails").split(",")));
			user.setEmailFrequency(set.getString("EmailFrequency"));
		}
		session.commitAndClose();
		
		return user;
	}

}
