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
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = "UserName")
	private byte[] userName;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = "UserEmail")
	private byte[] userEmail;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = "UserEmailPassword")
	private byte[] userEmailPassword;
	
	@DatabaseField(dataType = DataType.SERIALIZABLE, columnName = "PartnerEmails")
	private ArrayList<byte[]> partnerEmails = new ArrayList<>();
	
	@DatabaseField(columnName = "EmailFrequency")
	private String emailFrequency = "";
	
	@DatabaseField(columnName = "EmailLastSentDate")
	private long emailLastSentDate = 0;
	
	public User() {
		
	}
	
	public int getUserId() {
		return userId;
	}

	public byte[] getUserName() {
		return userName;
	}
	
	public void setUserName(byte[] userName) {
		this.userName = userName;
	}
	
	public byte[] getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(byte[] userEmail) {
		this.userEmail = userEmail;
	}
	
	public byte[] getUserEmailPassword() {
		return userEmailPassword;
	}
	
	public void setUserEmailPassword(byte[] userEmailPassword) {
		this.userEmailPassword = userEmailPassword;
	}
	
	public ArrayList<byte[]> getPartnerEmails() {
		return partnerEmails;
	}
	
	public void setPartnerEmails(ArrayList<byte[]> partnerEmails) {
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
