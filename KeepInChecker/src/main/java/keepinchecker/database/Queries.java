package keepinchecker.database;

import java.sql.ResultSet;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.KeepInCheckerPacket;
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
			user.setEmailLastSentDate(set.getLong("EmailLastSentDate"));
		}
		session.close();
		
		return user;
	}
	
	public static long getEmailLastSentDate() throws Exception {
		long lastEmailSentDate = 0;
		
		if (Constants.USER != null) {
			DbSession session = new DbSession(Constants.DATABASE_PATH);
			ResultSet set = session.find("SELECT EmailLastSentDate FROM User");
			if (set.next()) {
				lastEmailSentDate = set.getLong("EmailLastSentDate");
			}
			
			session.close();
		}
		
		return lastEmailSentDate;
	}
	
	public static void saveEmailLastSentDate(long date) throws Exception {
		if (Constants.USER == null) {
			return;
		}
		
		DbSession session = new DbSession(Constants.DATABASE_PATH);
		session.execute("UPDATE User SET EmailLastSentDate = " + date);
		session.commitAndClose();
		
		Constants.USER = getUser();
	}
	
	public static void savePackets(List<KeepInCheckerPacket> packets) throws Exception {
		DbSession session = new DbSession(Constants.DATABASE_PATH);
		ZoneId currentTimezone = ZonedDateTime.now().getZone();
		
		for (KeepInCheckerPacket packet : packets) {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO Packet (DateReceived, Timezone, Get, Host, Referer) ");
			sql.append("VALUES(");
			sql.append(packet.getTimestamp() + ", ");
			sql.append("'" + currentTimezone.getId() + "', ");
			sql.append("'" + packet.getGetValue() + "', ");
			sql.append("'" + packet.getHostValue() + "', ");
			sql.append("'" + packet.getRefererValue() + "')");
			
			session.execute(sql.toString());
		}
		
		session.commitAndClose();
	}
	
	public static List<KeepInCheckerPacket> getPackets() throws Exception {
		List<KeepInCheckerPacket> packets = new ArrayList<>();
		
		DbSession session = new DbSession(Constants.DATABASE_PATH);
		ResultSet set = session.find("SELECT * FROM Packet");
		if (set.next()) {
			KeepInCheckerPacket packet = new KeepInCheckerPacket();
			
			packet.setTimestamp(set.getLong("DateReceived"));
			packet.setTimezone(set.getString("Timezone"));
			packet.setGetValue(set.getString("Get"));
			packet.setHostValue(set.getString("Host"));
			packet.setRefererValue(set.getString("Referer"));
			
			packets.add(packet);
		}
		session.close();
		
		Collections.sort(packets, new Comparator<KeepInCheckerPacket>() {

			@Override
			public int compare(KeepInCheckerPacket packet1, KeepInCheckerPacket packet2) {
				return Long.compare(packet1.getTimestamp(), packet2.getTimestamp());
			}
		});
		
		return packets;
	}

}
