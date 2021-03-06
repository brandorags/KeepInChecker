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

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.Recipient;

import keepinchecker.database.entity.User;
import keepinchecker.setup.KeepInCheckerTestCase;

public class EmailUtilitiesTest extends KeepInCheckerTestCase {
	
	@Test
	public void testCanEmailBeSentAllUserCredentialsPresent() throws Exception {
		User user = new User();
		user.setUserEmail("test@example.com".getBytes(StandardCharsets.UTF_8));
		user.setUserEmailPassword("password".getBytes(StandardCharsets.UTF_8));
		user.setPartnerEmails(new ArrayList<>(Arrays.asList("partner1@example.com".getBytes(StandardCharsets.UTF_8),
				"partner2@example.com".getBytes(StandardCharsets.UTF_8))));
		
		assertTrue("Email should be able to be sent", EmailUtilities.canEmailBeSent(user));
	}
	
	@Test
	public void testCanEmailBeSentNotAllUserCredentialsPresent() throws Exception {
		User user = new User();
		user.setUserEmail("test@example.com".getBytes(StandardCharsets.UTF_8));
		user.setPartnerEmails(new ArrayList<>(Arrays.asList("partner1@example.com".getBytes(StandardCharsets.UTF_8),
				"partner2@example.com".getBytes(StandardCharsets.UTF_8))));
		
		assertFalse("Email should not be able to be sent", EmailUtilities.canEmailBeSent(user));
	}
	
	@Test
	public void testCanEmailBeSentUserIsNull() throws Exception {
		assertFalse("Email should not be able to be sent", EmailUtilities.canEmailBeSent(null));
	}

	@Test
	public void testCanEmailBeSentScheduledEmailHasBeenAlreadySent() throws Exception {
		User user = new User();
		user.setUserEmail("test@example.com".getBytes(StandardCharsets.UTF_8));
		user.setUserEmailPassword("password".getBytes(StandardCharsets.UTF_8));
		user.setPartnerEmails(new ArrayList<>(Arrays.asList("partner1@example.com".getBytes(StandardCharsets.UTF_8),
				"partner2@example.com".getBytes(StandardCharsets.UTF_8))));		
		user.setEmailFrequency(User.EMAIL_FREQUENCY_WEEKLY);
		user.setEmailLastSentDate(getTimeFromThePast(6));
		
		assertFalse("Email should not be able to be sent", EmailUtilities.canEmailBeSent(user));
	}
	
	@Test
	public void testHasScheduledEmailBeenSentWeekly() throws Exception {
		User user = new User();
		user.setEmailFrequency(User.EMAIL_FREQUENCY_WEEKLY);
		user.setEmailLastSentDate(getTimeFromThePast(6));
		
		assertTrue("Scheduled email should have already been sent", EmailUtilities.hasScheduledEmailBeenSent(user));
	}
	
	@Test
	public void testHasScheduledEmailBeenSentDaily() throws Exception {
		User user = new User();
		user.setEmailFrequency(User.EMAIL_FREQUENCY_DAILY);
		user.setEmailLastSentDate(getTimeFromThePast(1));
		
		assertFalse("Scheduled email should not have been sent", EmailUtilities.hasScheduledEmailBeenSent(user));
	}
	
	@Test
	public void testCreateEmail() throws Exception {
		User user = new User();
		user.setUserName("Test User".getBytes(StandardCharsets.UTF_8));
		user.setUserEmail("test@example.com".getBytes(StandardCharsets.UTF_8));
		user.setPartnerEmails(new ArrayList<>(Arrays.asList("partner1@example.com".getBytes(StandardCharsets.UTF_8),
				"partner2@example.com".getBytes(StandardCharsets.UTF_8))));	
		
		Email email = EmailUtilities.createEmail(user);
		
		assertEquals("Username should have been set", "Test User", email.getFromRecipient().getName());
		assertEquals("User email should have been set", "test@example.com", email.getFromRecipient().getAddress());
		assertEquals("Email should have two partner emails", 2, email.getRecipients().size());
		for (Recipient partnerEmail : email.getRecipients()) {
			assertEquals("Partner email should be BCC", RecipientType.BCC, partnerEmail.getType());
		}
	}
	
	@Test
	public void testGetMailServerGoogle() throws Exception {
		String emailAddress = "example@gmail.com";
		assertEquals("Should have the Google mail server", EmailUtilities.GOOGLE_MAIL_SERVER, EmailUtilities.getMailServer(emailAddress));
	}

	@Test
	public void testGetMailServerMicrosoft() throws Exception {
		String emailAddress = "example@outlook.com";
		assertEquals("Should have the Microsoft mail server", EmailUtilities.MICROSOFT_MAIL_SERVER, EmailUtilities.getMailServer(emailAddress));
	}

	@Test
	public void testGetMailServerYahoo() throws Exception {
		String emailAddress = "example@yahoo.com";
		assertEquals("Should have the Yahoo mail server", EmailUtilities.YAHOO_MAIL_SERVER, EmailUtilities.getMailServer(emailAddress));
	}

	@Test
	public void testGetMailServerAol() throws Exception {
		String emailAddress = "example@aol.com";
		assertEquals("Should have the AOL mail server", EmailUtilities.AOL_MAIL_SERVER, EmailUtilities.getMailServer(emailAddress));
	}

	@Test
	public void testGetMailServerComcast() throws Exception {
		String emailAddress = "example@comcast.net";
		assertEquals("Should have the Comcast mail server", EmailUtilities.COMCAST_MAIL_SERVER, EmailUtilities.getMailServer(emailAddress));
	}

	@Test
	public void testGetMailServerVerizon() throws Exception {
		String emailAddress = "example@verizon.net";
		assertEquals("Should have the Verizon mail server", EmailUtilities.VERIZON_MAIL_SERVER, EmailUtilities.getMailServer(emailAddress));
	}

	@Test
	public void testGetMailServerAtt() throws Exception {
		String emailAddress = "example@att.net";
		assertEquals("Should have the AT&T mail server", EmailUtilities.ATT_MAIL_SERVER, EmailUtilities.getMailServer(emailAddress));
	}
	
	@Test
	public void testGetPortFiveEightySeven() throws Exception {
		String mailServer = EmailUtilities.GOOGLE_MAIL_SERVER;
		assertEquals("The port number should be " + EmailUtilities.PORT_FIVE_EIGHTY_SEVEN, EmailUtilities.PORT_FIVE_EIGHTY_SEVEN, EmailUtilities.getPort(mailServer));
	}

	@Test
	public void testGetPortFourSixtyFive() throws Exception {
		String mailServer = EmailUtilities.VERIZON_MAIL_SERVER;
		assertEquals("The port number should be " + EmailUtilities.PORT_FOUR_SIXTY_FIVE, EmailUtilities.PORT_FOUR_SIXTY_FIVE, EmailUtilities.getPort(mailServer));
	}
	
	private long getTimeFromThePast(int daysToSubtract) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -daysToSubtract);
		
		return calendar.getTimeInMillis();
	}

}
