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

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import keepinchecker.database.entity.KeepInCheckerPacket;

public class PacketSnifferTest {
	
	PacketSniffer packetSniffer;

	@Test
	public void testAreGetHostAndRefererValuesEmptyAllAreNotEmpty() throws Exception {
		packetSniffer = new PacketSniffer();
		
		KeepInCheckerPacket packet = new KeepInCheckerPacket();
		packet.setGetValue("Get Value is this".getBytes(StandardCharsets.UTF_8));
		packet.setHostValue("Host value is here".getBytes(StandardCharsets.UTF_8));
		packet.setRefererValue("Referer value is this one".getBytes(StandardCharsets.UTF_8));
		
		boolean isEmpty = packetSniffer.areGetHostAndRefererValuesEmpty(packet);
		
		assertFalse("All values should not be empty", isEmpty);
	}

	@Test
	public void testAreGetHostAndRefererValuesEmptyHostIsEmpty() throws Exception {
		packetSniffer = new PacketSniffer();
		
		KeepInCheckerPacket packet = new KeepInCheckerPacket();
		packet.setGetValue("Get Value is this".getBytes(StandardCharsets.UTF_8));
		packet.setRefererValue("Referer value is this one".getBytes(StandardCharsets.UTF_8));
		
		boolean isEmpty = packetSniffer.areGetHostAndRefererValuesEmpty(packet);
		
		assertFalse("All values should not be empty", isEmpty);
	}
	
}
