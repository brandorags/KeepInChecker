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


package keepinchecker.utility;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.lang3.StringUtils;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

import keepinchecker.constants.Constants;
import keepinchecker.database.KeepInCheckerPacketManager;
import keepinchecker.database.UserManager;
import keepinchecker.database.entity.KeepInCheckerPacket;
import keepinchecker.database.entity.User;

public class EmailUtilities {
	
	protected static final String GOOGLE_MAIL_SERVER = "smtp.gmail.com";
	protected static final String MICROSOFT_MAIL_SERVER = "smtp-mail.outlook.com";
	protected static final String YAHOO_MAIL_SERVER = "smtp.mail.yahoo.com";
	protected static final String AOL_MAIL_SERVER = "smtp.aol.com";
	protected static final String COMCAST_MAIL_SERVER = "smtp.comcast.net";
	protected static final String VERIZON_MAIL_SERVER = "smtp.verizon.net";
	protected static final String ATT_MAIL_SERVER = "smtp.att.net";
	
	protected static final int PORT_FIVE_EIGHTY_SEVEN = 587;
	protected static final int PORT_FOUR_SIXTY_FIVE = 465;
	
	private static final KeepInCheckerPacketManager packetManager = new KeepInCheckerPacketManager();
	private static final UserManager userManager = new UserManager();
	
	public static void sendScheduledEmail() throws Exception {
		if (Constants.USER.getEmailLastSentDate() == 0) {
			setEmailLastSentDate();
		}
		
		if (canEmailBeSent(Constants.USER)) {
			Email email = createEmail(Constants.USER);
			Mailer mailer = new Mailer("smtp.gmail.com", 587, Constants.USER.getUserEmail(),
					Constants.USER.getUserEmailPassword(), TransportStrategy.SMTP_TLS);
			
			mailer.sendMail(email);
			
			setEmailLastSentDate();
		}
	}
	
	protected static boolean canEmailBeSent(User user) {
		if (user == null) {
			return false;
		}
		
		if (StringUtils.isEmpty(user.getUserEmail()) ||
				StringUtils.isEmpty(user.getUserEmailPassword()) ||
				user.getPartnerEmails().isEmpty()) {
			return false;
		}
		
		if (hasScheduledEmailBeenSent(user)) {
			return false;
		}
		
		return true;
	}
	
	protected static boolean hasScheduledEmailBeenSent(User user) {
		String emailFrequency = user.getEmailFrequency();
		long emailLastSentDate = user.getEmailLastSentDate();
		Duration timeBetweenLastEmailSentToNow = Duration.between(
				new Date(emailLastSentDate).toInstant(), Instant.now());
		
		if (StringUtils.equals(emailFrequency, User.EMAIL_FREQUENCY_WEEKLY) &&
				timeBetweenLastEmailSentToNow.toDays() < 7) {
			return true;
		} else if (StringUtils.equals(emailFrequency, User.EMAIL_FREQUENCY_DAILY) &&
				timeBetweenLastEmailSentToNow.toDays() < 1) {
			return true;
		}
		
		return false;
	}
	
	protected static Email createEmail(User user) throws Exception {
	    Email email = new Email();
	    
	    email.setFromAddress(user.getUserName(), user.getUserEmail());
	    for (String partnerEmail : user.getPartnerEmails()) {	    	
	    	email.addRecipient("", partnerEmail, RecipientType.BCC);
	    }
	    email.setSubject("KeepInChecker User Activity Report for " + user.getUserName());
	    email.setText(generateBodyText(user.getUserName()));
	    
	    return email;
	}
	
	protected static String getMailServer(String senderEmail) {
		String mailServer = "";
		
		List<String> googleDomains = Arrays.asList("gmail", "googlemail");
		List<String> microsoftDomains = Arrays.asList("hotmail", "outlook", "msn", "live", "passport");
		List<String> yahooDomains = Arrays.asList("yahoo", "ymail");
		List<String> aolDomains = Arrays.asList("aol", "aim");
		List<String> comcastDomains = Arrays.asList("comcast");
		List<String> verizonDomains = Arrays.asList("verizon");
		List<String> attDomains = Arrays.asList("att");
		
		// get the domain portion of the email
		String senderEmailDomain = StringUtils.substringAfter(senderEmail, "@");
		// remove the TLD portion
		senderEmailDomain = StringUtils.substringBeforeLast(senderEmailDomain, ".");
		
		if (googleDomains.contains(senderEmailDomain)) {
			mailServer = GOOGLE_MAIL_SERVER;
		} else if (microsoftDomains.contains(senderEmailDomain)) {
			mailServer = MICROSOFT_MAIL_SERVER;
		} else if (yahooDomains.contains(senderEmailDomain)) {
			mailServer = YAHOO_MAIL_SERVER;
		} else if (aolDomains.contains(senderEmailDomain)) {
			mailServer = AOL_MAIL_SERVER;
		} else if (comcastDomains.contains(senderEmailDomain)) {
			mailServer = COMCAST_MAIL_SERVER;
		} else if (verizonDomains.contains(senderEmailDomain)) {
			mailServer = VERIZON_MAIL_SERVER;
		} else if (attDomains.contains(senderEmailDomain)) {
			mailServer = ATT_MAIL_SERVER;
		}
		
		return mailServer;
	}
	
	protected static int getPort(String mailServer) {
		int port = 0;
		
		List<String> fiveEightySevenPortDomains = Arrays.asList(GOOGLE_MAIL_SERVER, MICROSOFT_MAIL_SERVER, 
				YAHOO_MAIL_SERVER, AOL_MAIL_SERVER, COMCAST_MAIL_SERVER);
		List<String> fourSixtyFivePortDomains = Arrays.asList(VERIZON_MAIL_SERVER, ATT_MAIL_SERVER);
		
		if (fiveEightySevenPortDomains.contains(mailServer)) {
			port = PORT_FIVE_EIGHTY_SEVEN;
		} else if (fourSixtyFivePortDomains.contains(mailServer)) {
			port = PORT_FOUR_SIXTY_FIVE;
		}
		
		return port;
	}
	
	private static String generateBodyText(String senderName) throws Exception {
		StringBuilder bodyText = new StringBuilder();
		
		bodyText.append("Hello,\n\nOn behalf of " + senderName + ", you have received this" +
				" email with the following data,\n\n");
		
		List<KeepInCheckerPacket> packets = packetManager.getPackets();
		if (!packets.isEmpty()) {
			StringBuilder packetData = new StringBuilder();
			for (KeepInCheckerPacket packet : packets) {
				packetData.append("\n" + new Date(packet.getTimestamp()).toString() + " " + packet.getTimezone() +
						" " + packet.getGetValue() + " " + packet.getHostValue() + " " + packet.getRefererValue());
			}
			
			bodyText.append(packets.size() + " questionable sites were visted:\n");
			bodyText.append(packetData.toString());	
		} else {
			bodyText.append("0 questionable sites were visited.");
		}
		
		bodyText.append("\n\nKeep staying accountable!\n\nSincerely,\nKeepInChecker");
		
		return bodyText.toString();
	}
	
	private static void setEmailLastSentDate() throws SQLException, IOException {
		User user = new User();
		user.setEmailLastSentDate(new Date().getTime());
		userManager.saveUser(user);
	}
	
}
