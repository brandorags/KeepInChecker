package keepinchecker.network;

public class KeepInCheckerPacket {
	
	private long timestamp;
	private String getValue;
	private String hostValue;
	private String refererValue;
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
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
