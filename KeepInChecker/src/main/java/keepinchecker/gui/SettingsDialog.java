package keepinchecker.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class SettingsDialog {

	protected Shell settingsShell;
	
	public SettingsDialog() {
		
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		settingsShell.open();
		settingsShell.layout();
		while (!settingsShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		settingsShell = new Shell();
		settingsShell.setSize(450, 300);
		settingsShell.setText("Settings");
	}
	
}
