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
			return value;
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
