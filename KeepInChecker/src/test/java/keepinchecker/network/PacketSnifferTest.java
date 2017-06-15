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
