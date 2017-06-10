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


package keepinchecker.network;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapIpV4Address;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

import keepinchecker.constants.Constants;
import keepinchecker.database.KeepInCheckerPacketManager;
import keepinchecker.database.entity.KeepInCheckerPacket;
import keepinchecker.utility.SecurityUtilities;

public class PacketSniffer {
	
	private static final KeepInCheckerPacketManager packetManager = new KeepInCheckerPacketManager();
	
	private Map<Timestamp, Packet> packetMap = new HashMap<>();
	
	public void sniffPackets() throws Exception {
		PcapNetworkInterface networkInterface = getNetworkInterface();
	    PcapHandle handle = networkInterface.openLive(65536, PromiscuousMode.PROMISCUOUS, 5000);

	    handle.loop(2000, new KeepInCheckerPacketListener(handle));
	    
	    sendPacketsToDatabase(packetMap);
	    
	    packetMap.clear();
	}
	
	private PcapNetworkInterface getNetworkInterface() throws PcapNativeException {
		PcapNetworkInterface networkInterface = null;
		List<PcapNetworkInterface> networkInterfaces = Pcaps.findAllDevs();
		if (networkInterfaces.isEmpty()) {
			return null;
		}
		
		for (PcapNetworkInterface nif : networkInterfaces) {	
			List<PcapAddress> addresses = nif.getAddresses();
			if (addresses.isEmpty()) {
				continue;
			}
			
			for (PcapAddress address : addresses) {
				if (address instanceof PcapIpV4Address &&
						!address.getAddress().isLoopbackAddress()) {	
					networkInterface = nif;
					break;
				}
			}
		}
		
		return networkInterface;
	} 
	
	private void storePacket(Packet packet, PcapHandle handle) {
		packetMap.put(handle.getTimestamp(), packet);
	}
	
	private void sendPacketsToDatabase(Map<Timestamp, Packet> packetMap) throws Exception {
		List<KeepInCheckerPacket> objectionablePackets = new ArrayList<>();
		
		for (Map.Entry<Timestamp, Packet> entry : packetMap.entrySet()) {
			Timestamp packetTime = entry.getKey();
			String packetString = PacketParser.convertToHumanReadableFormat(entry.getValue());
			
			for (String objectionableWord : Constants.OBJECTIONABLE_WORDS) {
				if (StringUtils.contains(packetString, objectionableWord)) {
					KeepInCheckerPacket packet = new KeepInCheckerPacket();
					packet.setTimestamp(packetTime.getTime());
					
					String parsedGetValue = PacketParser.parse(PacketParser.GET, packetString);
					String parsedHostValue = PacketParser.parse(PacketParser.HOST, packetString);
					String parsedReferValue = PacketParser.parse(PacketParser.REFERER, packetString);
					packet.setGetValue(SecurityUtilities.encrypt(parsedGetValue));
					packet.setHostValue(SecurityUtilities.encrypt(parsedHostValue));
					packet.setRefererValue(SecurityUtilities.encrypt(parsedReferValue));
					
					if (!areGetHostAndRefererValuesEmpty(packet)) {						
						objectionablePackets.add(packet);
					}
					
					break;
				}
			}
		}
		
		if (!objectionablePackets.isEmpty()) {			
			packetManager.savePackets(objectionablePackets);
		}
	}
	
	private boolean areGetHostAndRefererValuesEmpty(KeepInCheckerPacket packet) {
		if (StringUtils.isEmpty(packet.getGetValue().toString()) &&
				StringUtils.isEmpty(packet.getHostValue().toString()) &&
				StringUtils.isEmpty(packet.getRefererValue().toString())) {
			return true;
		}
		
		return false;
	}
	
	private class KeepInCheckerPacketListener implements PacketListener {
		
		private PcapHandle handle;
		
		public KeepInCheckerPacketListener(PcapHandle handle) {
			this.handle = handle;
		}

		@Override
		public void gotPacket(Packet packet) {
			storePacket(packet, handle);
		}
		
	}

}
