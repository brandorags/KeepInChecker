package keepinchecker.network;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PacketParserTest {
	
	private String packetOutput = "GET / HTTP/1.1\n" +
			"Host: brandonragsdale.com\n" +
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
		String getValue = PacketParser.parse(PacketParser.GET, packetOutput);
		assertEquals("/", getValue);
	}
	
}
