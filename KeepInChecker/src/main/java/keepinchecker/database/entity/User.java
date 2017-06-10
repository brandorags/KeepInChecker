package keepinchecker.database.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "User")
public class User implements Serializable {
	
	private static final long serialVersionUID = -5884713152979754661L;
	
	public static final String EMAIL_FREQUENCY_DAILY = "Daily";
	public static final String EMAIL_FREQUENCY_WEEKLY = "Weekly";
	
	@DatabaseField(generatedId = true, columnName = "UserId")
	private int userId;
	
	@DatabaseField(columnName = "UserName")
	private String userName = "";
	
	@DatabaseField(columnName = "UserEmail")
	private String userEmail = "";
	
	@DatabaseField(columnName = "UserEmailPassword")
	private String userEmailPassword = "";
	
	@DatabaseField(dataType = DataType.SERIALIZABLE, columnName = "PartnerEmails")
	private ArrayList<String> partnerEmails = new ArrayList<>();
	
	@DatabaseField(columnName = "EmailFrequency")
	private String emailFrequency = "";
	
	@DatabaseField(columnName = "EmailLastSentDate")
	private long emailLastSentDate = 0;
	
	public User() {
		
	}
	
	public int getUserId() {
		return userId;
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
	
	public ArrayList<String> getPartnerEmails() {
		return partnerEmails;
	}
	
	public void setPartnerEmails(ArrayList<String> partnerEmails) {
		this.partnerEmails = partnerEmails;
	}
	
	public String getEmailFrequency() {
		return emailFrequency;
	}
	
	public void setEmailFrequency(String emailFrequency) {
		this.emailFrequency = emailFrequency;
	}

	public long getEmailLastSentDate() {
		return emailLastSentDate;
	}

	public void setEmailLastSentDate(long emailLastSentDate) {
		this.emailLastSentDate = emailLastSentDate;
	}

}
