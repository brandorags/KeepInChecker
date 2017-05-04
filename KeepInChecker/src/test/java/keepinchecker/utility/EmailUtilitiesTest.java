package keepinchecker.utility;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.Recipient;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.User;

public class EmailUtilitiesTest {
	
	@Test
	public void testCanEmailBeSentAllUserCredentialsPresent() throws Exception {
		User user = new User();
		user.setUserEmail("test@example.com");
		user.setUserEmailPassword("password");
		user.setPartnerEmails(Arrays.asList("partner1@example.com", "partner2@example.com"));
		
		assertTrue("Email should be able to be sent", EmailUtilities.canEmailBeSent(user));
	}
	
	@Test
	public void testCanEmailBeSentNotAllUserCredentialsPresent() throws Exception {
		User user = new User();
		user.setUserEmail("test@example.com");
		user.setPartnerEmails(Arrays.asList("partner1@example.com", "partner2@example.com"));
		
		assertFalse("Email should not be able to be sent", EmailUtilities.canEmailBeSent(user));
	}
	
	@Test
	public void testCanEmailBeSentUserIsNull() throws Exception {
		assertFalse("Email should not be able to be sent", EmailUtilities.canEmailBeSent(null));
	}

	@Test
	public void testCanEmailBeSentScheduledEmailHasBeenAlreadySent() throws Exception {
		User user = new User();
		user.setUserEmail("test@example.com");
		user.setUserEmailPassword("password");
		user.setPartnerEmails(Arrays.asList("partner1@example.com", "partner2@example.com"));
		user.setEmailFrequency(Constants.USER_EMAIL_FREQUENCY_WEEKLY);
		user.setEmailLastSentDate(getTimeFromThePast(6));
		
		assertFalse("Email should not be able to be sent", EmailUtilities.canEmailBeSent(user));
	}
	
	@Test
	public void testHasScheduledEmailBeenSentWeekly() throws Exception {
		User user = new User();
		user.setEmailFrequency(Constants.USER_EMAIL_FREQUENCY_WEEKLY);
		user.setEmailLastSentDate(getTimeFromThePast(6));
		
		assertTrue("Scheduled email should have already been sent", EmailUtilities.hasScheduledEmailBeenSent(user));
	}
	
	@Test
	public void testHasScheduledEmailBeenSentDaily() throws Exception {
		User user = new User();
		user.setEmailFrequency(Constants.USER_EMAIL_FREQUENCY_DAILY);
		user.setEmailLastSentDate(getTimeFromThePast(1));
		
		assertFalse("Scheduled email should not have been sent", EmailUtilities.hasScheduledEmailBeenSent(user));
	}
	
	@Test
	public void testCreateEmail() throws Exception {
		User user = new User();
		user.setUserName("Test User");
		user.setUserEmail("test@example.com");
		user.setPartnerEmails(Arrays.asList("partner1@example.com", "partner2@example.com"));
		
		Email email = EmailUtilities.createEmail(user);
		
		assertEquals("Username should have been set", "Test User", email.getFromRecipient().getName());
		assertEquals("User email should have been set", "test@example.com", email.getFromRecipient().getAddress());
		assertEquals("Email should have two partner emails", 2, email.getRecipients().size());
		for (Recipient partnerEmail : email.getRecipients()) {
			assertEquals("Partner email should be BCC", RecipientType.BCC, partnerEmail.getType());
		}
	}
	
	private long getTimeFromThePast(int daysToSubtract) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -daysToSubtract);
		
		return calendar.getTimeInMillis();
	}

}
