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


package keepinchecker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;

public class KeepInCheckerSystemTray {
	
	private SystemTray systemTray;
	private Menu menu;
	private MenuItem settingsMenuItem;
	private MenuItem aboutMenuItem;
	private MenuItem quitMenuItem;
	
	public KeepInCheckerSystemTray() {

	}
	
	public void run() {
		// load system tray
		systemTray = SystemTray.get();
		if (systemTray == null) {
			throw new RuntimeException("An error occurred while attempting to open the system tray.");
		}		
		systemTray.setTooltip("KeepInChecker");
		systemTray.setImage(getClass().getResource("images/chevron-up.png"));
		
		// load menu
		menu = systemTray.getMenu();
		
		// load menu items
		settingsMenuItem = new MenuItem("Settings", new SettingsActionListener());
		menu.add(settingsMenuItem);
		
		aboutMenuItem = new MenuItem("About", new AboutActionListener());
		menu.add(aboutMenuItem);
		
		menu.add(new Separator());
		
		quitMenuItem = new MenuItem("Quit", new QuitActionListener());
		menu.add(quitMenuItem);
	}
	
	private class SettingsActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SettingsDialog settingsDialog = new SettingsDialog();
			settingsDialog.open();
		}
		
	}
	
	private class AboutActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO: create about dialog
			
		}
		
	}
	
	private class QuitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			systemTray.shutdown();
			System.exit(0);
		}
		
	}
    
}
