package keepinchecker.database.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Packet")
public class KeepInCheckerPacket implements Serializable {
	
	private static final long serialVersionUID = 3512272028255756831L;
	
	@DatabaseField(id = true, columnName = "Timestamp")
	private long timestamp;
	
	@DatabaseField(columnName = "Timezone")
	private String timezone;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = "Get")
	private byte[] getValue;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = "Host")
	private byte[] hostValue;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = "Referer")
	private byte[] refererValue;
	
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

	public byte[] getGetValue() {
		return getValue;
	}
	
	public void setGetValue(byte[] getValue) {
		this.getValue = getValue;
	}
	
	public byte[] getHostValue() {
		return hostValue;
	}
	
	public void setHostValue(byte[] hostValue) {
		this.hostValue = hostValue;
	}
	
	public byte[] getRefererValue() {
		return refererValue;
	}
	
	public void setRefererValue(byte[] refererValue) {
		this.refererValue = refererValue;
	}

}
