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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;			
		}
		
		if (obj == null) {			
			return false;
		}
		
		if (getClass() != obj.getClass()) {			
			return false;
		}
		
		KeepInCheckerPacket other = (KeepInCheckerPacket) obj;
		if (timestamp != other.timestamp) {			
			return false;
		}
		
		return true;
	}
	
}
