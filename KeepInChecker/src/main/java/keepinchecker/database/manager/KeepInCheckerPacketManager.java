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


package keepinchecker.database.manager;

import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.KeepInCheckerPacket;
import keepinchecker.utility.SecurityUtilities;

public class KeepInCheckerPacketManager {

	private ConnectionSource connectionSource;
	private Dao<KeepInCheckerPacket, Long> packetDao;
	
	public void savePackets(List<KeepInCheckerPacket> packets) throws Exception {
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_PATH);
			packetDao = DaoManager.createDao(connectionSource, KeepInCheckerPacket.class);
			
			encryptSettings(packets);
			
			packetDao.create(packets);
		} finally {
			connectionSource.close();
		}
	}
	
	public List<KeepInCheckerPacket> getPackets() throws Exception {
		List<KeepInCheckerPacket> packets = null;
		
		try {
			connectionSource = new JdbcConnectionSource(Constants.DATABASE_PATH);
			packetDao = DaoManager.createDao(connectionSource, KeepInCheckerPacket.class);
			
			packets = packetDao.queryForAll();
			
			if (packets != null && !packets.isEmpty()) {				
				decryptSettings(packets);
			}
		} finally {
			connectionSource.close();
		}
		
		return packets;
	}
	
	private void encryptSettings(List<KeepInCheckerPacket> packets) throws Exception {
		for (KeepInCheckerPacket packet : packets) {
			packet.setGetValue(SecurityUtilities.encrypt(packet.getGetValue()));
			packet.setHostValue(SecurityUtilities.encrypt(packet.getHostValue()));
			packet.setRefererValue(SecurityUtilities.encrypt(packet.getRefererValue()));
		}
	}

	private void decryptSettings(List<KeepInCheckerPacket> packets) throws Exception {
		for (KeepInCheckerPacket packet : packets) {
			packet.setGetValue(SecurityUtilities.decrypt(packet.getGetValue()));
			packet.setHostValue(SecurityUtilities.decrypt(packet.getHostValue()));
			packet.setRefererValue(SecurityUtilities.decrypt(packet.getRefererValue()));
		}
	}
	
}
