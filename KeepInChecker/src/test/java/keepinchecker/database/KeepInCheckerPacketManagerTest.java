package keepinchecker.database;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import keepinchecker.database.entity.KeepInCheckerPacket;
import keepinchecker.database.manager.KeepInCheckerPacketManager;
import keepinchecker.setup.KeepInCheckerTestCase;

public class KeepInCheckerPacketManagerTest extends KeepInCheckerTestCase {
	
	private KeepInCheckerPacketManager packetManager;

	@Test
	public void testSaveGetPackets() throws Exception {
		packetManager = new KeepInCheckerPacketManager();
		
		ZoneId currentTimezone = ZonedDateTime.now().getZone();
		KeepInCheckerPacket packet1 = new KeepInCheckerPacket();
		packet1.setTimestamp(1111);
		packet1.setTimezone(currentTimezone.getId());
		packet1.setGetValue("Packet1GetValue".getBytes(StandardCharsets.UTF_8));
		packet1.setHostValue("Packet1HostValue".getBytes(StandardCharsets.UTF_8));
		packet1.setRefererValue("Packet1RefererValue".getBytes(StandardCharsets.UTF_8));

		KeepInCheckerPacket packet2 = new KeepInCheckerPacket();
		packet2.setTimestamp(2222);
		packet2.setTimezone(currentTimezone.getId());
		packet2.setGetValue("Packet2GetValue".getBytes(StandardCharsets.UTF_8));
		packet2.setHostValue("Packet2HostValue".getBytes(StandardCharsets.UTF_8));
		packet2.setRefererValue("Packet2RefererValue".getBytes(StandardCharsets.UTF_8));

		KeepInCheckerPacket packet3 = new KeepInCheckerPacket();
		packet3.setTimestamp(3333);
		packet3.setTimezone(currentTimezone.getId());
		packet3.setGetValue("Packet3GetValue".getBytes(StandardCharsets.UTF_8));
		packet3.setHostValue("Packet3HostValue".getBytes(StandardCharsets.UTF_8));
		packet3.setRefererValue("Packet3RefererValue".getBytes(StandardCharsets.UTF_8));
		
		packetManager.savePackets(Arrays.asList(packet1, packet2, packet3));
		List<KeepInCheckerPacket> packetsFromDb = packetManager.getPackets();
		
		assertEquals("All packets should have been saved to the database", 3, packetsFromDb.size());
		for (KeepInCheckerPacket packet : packetsFromDb) {
			switch ((int) packet.getTimestamp()) {
				case 1111:
					assertEquals("Timezone should have been saved to the database", currentTimezone.getId(), packet.getTimezone());
					assertEquals("Get value should have been saved to the database", "Packet1GetValue", new String(packet.getGetValue(), StandardCharsets.UTF_8));
					assertEquals("Host value should have been saved to the database", "Packet1HostValue", new String(packet.getHostValue(), StandardCharsets.UTF_8));
					assertEquals("Referer value should have been saved to the database", "Packet1RefererValue", new String(packet.getRefererValue(), StandardCharsets.UTF_8));
					break;
	
				case 2222:
					assertEquals("Timezone should have been saved to the database", currentTimezone.getId(), packet.getTimezone());
					assertEquals("Get value should have been saved to the database", "Packet2GetValue", new String(packet.getGetValue(), StandardCharsets.UTF_8));
					assertEquals("Host value should have been saved to the database", "Packet2HostValue", new String(packet.getHostValue(), StandardCharsets.UTF_8));
					assertEquals("Referer value should have been saved to the database", "Packet2RefererValue", new String(packet.getRefererValue(), StandardCharsets.UTF_8));					
					break;
					
				case 3333:
					assertEquals("Timezone should have been saved to the database", currentTimezone.getId(), packet.getTimezone());
					assertEquals("Get value should have been saved to the database", "Packet3GetValue", new String(packet.getGetValue(), StandardCharsets.UTF_8));
					assertEquals("Host value should have been saved to the database", "Packet3HostValue", new String(packet.getHostValue(), StandardCharsets.UTF_8));
					assertEquals("Referer value should have been saved to the database", "Packet3RefererValue", new String(packet.getRefererValue(), StandardCharsets.UTF_8));
					break;
					
				default:
					break;
			}
		}
	}
	
}
