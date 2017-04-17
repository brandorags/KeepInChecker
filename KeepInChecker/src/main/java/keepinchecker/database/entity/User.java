package keepinchecker.database.entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	
	private static final long serialVersionUID = -5884713152979754661L;
	
	private String userName;
	private String userEmail;
	private String userEmailPassword;
	private List<String> partnerEmails;
	private String emailFrequency;
	
	public User() {
		
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getUserEmailPassword() {
		return userEmailPassword;
	}
	
	public void setUserEmailPassword(String userEmailPassword) {
		this.userEmailPassword = userEmailPassword;
	}
	
	public List<String> getPartnerEmails() {
		return partnerEmails;
	}
	
	public void setPartnerEmails(List<String> partnerEmails) {
		this.partnerEmails = partnerEmails;
	}
	
	public String getEmailFrequency() {
		return emailFrequency;
	}
	
	public void setEmailFrequency(String emailFrequency) {
		this.emailFrequency = emailFrequency;
	}

}
