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

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.lang3.StringUtils;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

import keepinchecker.constants.Constants;
import keepinchecker.database.Queries;
import keepinchecker.database.entity.User;

public class EmailUtilities {
	
	private static long emailLastSentDate;
	
	static {
		try {
			emailLastSentDate = Queries.getEmailLastSentDate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendScheduledEmail() throws Exception {
		if (emailLastSentDate == 0) {
			emailLastSentDate = new Date().getTime();
			Queries.saveEmailLastSentDate(emailLastSentDate);
		}
		
		if (canEmailBeSent(Constants.USER)) {
			Email email = createEmail(Constants.USER);
			Mailer mailer = new Mailer("smtp.gmail.com", 587, Constants.USER.getUserEmail(),
					Constants.USER.getUserEmailPassword(), TransportStrategy.SMTP_TLS);
			
			mailer.sendMail(email);
			
			emailLastSentDate = new Date().getTime();
			Queries.saveEmailLastSentDate(emailLastSentDate);
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
		
		if (StringUtils.equals(emailFrequency, Constants.USER_EMAIL_FREQUENCY_WEEKLY) &&
				timeBetweenLastEmailSentToNow.toDays() < 7) {
			return true;
		} else if (StringUtils.equals(emailFrequency, Constants.USER_EMAIL_FREQUENCY_DAILY) &&
				timeBetweenLastEmailSentToNow.toDays() < 1) {
			return true;
		}
		
		return false;
	}
	
	protected static Email createEmail(User user) {
	    Email email = new Email();
	    
	    email.setFromAddress(user.getUserName(), user.getUserEmail());
	    for (String partnerEmail : user.getPartnerEmails()) {	    	
	    	email.addRecipient("", partnerEmail, RecipientType.BCC);
	    }
//	    email.setText("Subject text goes here.");
	    email.setSubject("KeepInChecker User Activity Report for " + user.getUserName());
	    email.setTextHTML("<p>Subject text goes here.</p>");
	    
	    return email;
	}
	
}
