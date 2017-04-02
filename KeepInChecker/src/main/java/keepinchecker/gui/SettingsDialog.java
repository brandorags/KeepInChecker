package keepinchecker.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;

public class SettingsDialog {

	protected Shell shell;
	
	private Text nameField;
	private Text emailField;
	private Text emailPasswordField;
	private Table partnersEmailsTable;

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		
		createContents();
		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Settings");
		
		Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setBounds(10, 29, 59, 14);
		nameLabel.setText("Name:");
		
		nameField = new Text(shell, SWT.BORDER);
		nameField.setBounds(117, 26, 323, 19);
		
		Label lblEmail = new Label(shell, SWT.NONE);
		lblEmail.setBounds(10, 54, 59, 14);
		lblEmail.setText("Email:");
		
		emailField = new Text(shell, SWT.BORDER);
		emailField.setBounds(117, 51, 323, 19);
		
		Label emailPasswordLabel = new Label(shell, SWT.NONE);
		emailPasswordLabel.setBounds(10, 79, 101, 14);
		emailPasswordLabel.setText("Email Password:");
		
		emailPasswordField = new Text(shell, SWT.BORDER);
		emailPasswordField.setBounds(117, 76, 323, 19);
		
		partnersEmailsTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		partnersEmailsTable.setBounds(117, 101, 323, 107);
		partnersEmailsTable.setHeaderVisible(true);
		partnersEmailsTable.setLinesVisible(true);
		
		Label partnersEmailsLabel = new Label(shell, SWT.NONE);
		partnersEmailsLabel.setBounds(10, 147, 103, 14);
		partnersEmailsLabel.setText("Partners' Emails:");
		
		Label emailFrequencyLabel = new Label(shell, SWT.NONE);
		emailFrequencyLabel.setBounds(10, 222, 101, 19);
		emailFrequencyLabel.setText("Email Frequency:");
		
		Combo emailFrequencyCombo = new Combo(shell, SWT.NONE);
		emailFrequencyCombo.setBounds(117, 219, 101, 31);
		
		Button cancelButton = new Button(shell, SWT.NONE);
		cancelButton.setBounds(346, 250, 94, 28);
		cancelButton.setText("Cancel");
		
		Button saveButton = new Button(shell, SWT.NONE);
		saveButton.setBounds(246, 250, 94, 28);
		saveButton.setText("Save");
	}
	
}
