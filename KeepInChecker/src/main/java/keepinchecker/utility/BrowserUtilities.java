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


package keepinchecker.utility;

import javax.xml.bind.DatatypeConverter;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.IpV4Packet.IpV4Header;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.NifSelector;

public class BrowserUtilities {
	
	public static void sniff_packets() throws Exception {
//	    String filter = null;
//	    if (args.length != 0) {
//	      filter = args[0];
//	    }

	    PcapNetworkInterface nif = new NifSelector().selectNetworkInterface();
	    if (nif == null) {
	      System.exit(1);
	    }

	    final PcapHandle handle = nif.openLive(65536, PromiscuousMode.PROMISCUOUS, 5000);

//	    if (filter != null && filter.length() != 0) {
//	      handle.setFilter(filter, BpfCompileMode.OPTIMIZE);
//	    }

	    PacketListener listener = new PacketListener() {
	      @Override
	      public void gotPacket(Packet packet) {
	        printPacket(packet, handle);
	      }
	    };

	    handle.loop(500, listener);
	}
	
	private static void printPacket(Packet packet, PcapHandle ph) {
		StringBuilder sb = new StringBuilder();
	    sb.append("A packet captured at ")
	    .append(ph.getTimestamp())
	    .append(":");
//	    System.out.println(sb);
//	    System.out.println(packet);
	    String hex = ByteArrays.toHexString(packet.getRawData(), "");
//	    System.out.println();
	    StringBuilder output = new StringBuilder();
	    for (int i = 0; i < hex.length(); i+=2) {
	        String str = hex.substring(i, i+2);
	        output.append((char)Integer.parseInt(str, 16));
	    }
	    System.out.println(output.toString());
	    if (output.toString().contains("brandon")) {
	    	System.out.println("---------------------- LOOK BELOW ------------------------");
//	    	System.out.println(hex);
	    	System.out.println("For loop: " + output.toString());
	    	System.out.println("DatatypeConverter: " + new String(DatatypeConverter.parseHexBinary(ByteArrays.toHexString(packet.getRawData(), ""))));
	    	System.out.println("---------------------- LOOK ABOVE ------------------------");	    	
	    } else {
	    }
	}

}
