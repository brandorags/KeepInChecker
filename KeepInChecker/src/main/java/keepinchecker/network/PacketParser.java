package keepinchecker.network;

import javax.xml.bind.DatatypeConverter;

import org.pcap4j.packet.Packet;
import org.pcap4j.util.ByteArrays;

public class PacketParser {
	
	public static final String GET = "GET";

	public static String parse(String keyword, String packet) {
		return "";
	}
	
	public static String convertToHumanReadableFormat(Packet packet) {
		String hexString = ByteArrays.toHexString(packet.getRawData(), "");
		byte[] hexBinary = DatatypeConverter.parseHexBinary(hexString);
		
		return new String(hexBinary);
	}
	
}
