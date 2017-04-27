package keepinchecker.network;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.ByteArrays;

public class PacketParser {

	public static final String GET = "GET";
	public static final String HOST = "Host";
	public static final String REFERER = "Referer";

	public static String parse(String keyword, String packet) {
		String value = "";

		if (!StringUtils.contains(packet, keyword)) {
			return "";
		}

		String[] packetSplitOnNewline = packet.split("\n");
		for (String line : packetSplitOnNewline) {
			if (StringUtils.equals(keyword, GET) && StringUtils.contains(line, keyword)) {
				value = StringUtils.substringBefore(line.split(keyword)[1], "HTTP");
				break;
			} else if ((StringUtils.contains(line, HOST) || 
					StringUtils.contains(line, REFERER)) && 
					StringUtils.contains(line, keyword)) {
				value = line.split(keyword + ":")[1];
				break;
			}
		}

		return value.trim();
	}

	public static String convertToHumanReadableFormat(Packet packet) {
		String hexString = ByteArrays.toHexString(packet.getRawData(), "");
		byte[] hexBinary = DatatypeConverter.parseHexBinary(hexString);

		return new String(hexBinary);
	}

}
