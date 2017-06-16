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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PacketParserTest {
	
	private String packetString = "GET / HTTP/1.1\n" +
			"Host: example.com\n" +
			"Connection: keep-alive\n" +
			"Cache-Control: max-age=0\n" +
			"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" +
			"Accept-Encoding: gzip, deflate\n" +
			"Accept-Language: en-US\n" +
			"DNT: 1\n" +
			"Upgrade-Insecure-Requests: 1\n" +
			"User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML,"
			+ " like Gecko) Chrome/57.0.2987.133 Safari/537.36\n";
	
	@Test
	public void testParseGet() throws Exception {
		String getValue = PacketParser.parse(PacketParser.GET, packetString);
		assertEquals("/", getValue);
	}
	
	@Test
	public void testParseHost() throws Exception {
		String hostValue = PacketParser.parse(PacketParser.HOST, packetString);
		assertEquals("example.com", hostValue);
	}
	
}
