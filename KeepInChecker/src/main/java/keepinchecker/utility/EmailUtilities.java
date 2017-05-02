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

import java.util.Date;

import javax.mail.internet.MimeMessage.RecipientType;

import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

import keepinchecker.constants.Constants;
import keepinchecker.database.Queries;

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
		if (Constants.USER == null) {
			return;
		}
		
		if (emailLastSentDate == 0) {
			Queries.saveEmailLastSentDate(new Date().getTime());
		}
		
	    final Email email = new Email();
	    email.setFromAddress("Sender's Name", "username@gmail.com");
	    email.addRecipient("Recipient's Name", "username@example.com", RecipientType.TO);
//	    email.setText("Subject text goes here.");
	    email.setTextHTML("<p>Subject text goes here.</p>");
	    email.setSubject("KISSES");

	    new Mailer("smtp.gmail.com", 587, "username@gmail.com", "hereiswherethepasswordgoes", TransportStrategy.SMTP_TLS).sendMail(email);
	}
	
}
