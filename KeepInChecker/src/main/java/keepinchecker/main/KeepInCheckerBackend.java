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


package keepinchecker.main;

import keepinchecker.constants.Constants;
import keepinchecker.gui.WelcomeDialog;
import keepinchecker.network.PacketSniffer;
import keepinchecker.utility.EmailUtilities;

public class KeepInCheckerBackend implements Runnable {

	@Override
	public void run() {
		if (Constants.USER == null) {
			WelcomeDialog welcomeDialog = new WelcomeDialog();
			welcomeDialog.open();
		}

		try {
			while (true) {
				Thread.sleep(10000);
				
				PacketSniffer packetSniffer = new PacketSniffer();
				packetSniffer.sniffPackets();	
				
				EmailUtilities.sendScheduledEmail();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
