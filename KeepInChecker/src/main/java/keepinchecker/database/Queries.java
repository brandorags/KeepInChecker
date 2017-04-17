package keepinchecker.database;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.User;

public class Queries {
	
	/**
	 * Inserts a new user's personal data, such as the username,
	 * email address, email password, etc., into the database.
	 * 
	 * @param userName - the name of the user (e.g., "John Doe")
	 * @param userEmail - the user's email address
	 * @param userEmailPassword - the password of the user's email address
	 * @param partnerEmails - the email addresses of the user's accountability partners
	 * @param emailFrequency - the frequency in which to send the emails (e.g., "Daily")
	 * @throws Exception 
	 */
	public static void saveUserData(String userName, String userEmail, String userEmailPassword,
			List<String> partnerEmails, String emailFrequency) throws Exception {
		DbSession session = new DbSession(Constants.DATABASE_PATH);
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO User (UserName, UserEmail, UserEmailPassword, PartnerEmails, EmailFrequency) ");
		sql.append("VALUES('" + userName + "', ");
		sql.append("'" + userEmail + "', ");
		sql.append("'" + userEmailPassword + "', ");
		sql.append("'" + String.join(",", partnerEmails) + "', ");
		sql.append("'" + emailFrequency + "')");
		
		session.execute(sql.toString());
		session.commitAndClose();
		
		// initialize or update current user
		User user = getUser();
		Constants.CURRENT_USER = user;
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
