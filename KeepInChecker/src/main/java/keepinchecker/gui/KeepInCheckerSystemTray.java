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
		systemTray.setImage(KeepInCheckerSystemTray.class.getResource("chevron-up.png"));
		
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
