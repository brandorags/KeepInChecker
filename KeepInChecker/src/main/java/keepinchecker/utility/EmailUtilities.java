package keepinchecker.utility;

import javax.mail.internet.MimeMessage.RecipientType;

import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

public class EmailUtilities {

	public static void sendScheduledEmail() {
	    final Email email = new Email();
	    email.setFromAddress("Sender's Name", "username@gmail.com");
	    email.addRecipient("Recipient's Name", "username@example.com", RecipientType.TO);
//	    email.setText("Subject text goes here.");
	    email.setTextHTML("<p>Subject text goes here.</p>");
	    email.setSubject("KISSES");

	    new Mailer("smtp.gmail.com", 587, "username@gmail.com", "hereiswherethepasswordgoes", TransportStrategy.SMTP_TLS).sendMail(email);
	}
	
}
