package keepinchecker.database.entity;

import java.io.Serializable;

public class KeepInCheckerPacket implements Serializable {
	
	private static final long serialVersionUID = 3512272028255756831L;
	
	private long timestamp;
	private String timezone;
	private String getValue;
	private String hostValue;
	private String refererValue;
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getGetValue() {
		return getValue;
	}
	
	public void setGetValue(String getValue) {
		this.getValue = getValue;
	}
	
	public String getHostValue() {
		return hostValue;
	}
	
	public void setHostValue(String hostValue) {
		this.hostValue = hostValue;
	}
	
	public String getRefererValue() {
		return refererValue;
	}
	
	public void setRefererValue(String refererValue) {
		this.refererValue = refererValue;
	}

}
