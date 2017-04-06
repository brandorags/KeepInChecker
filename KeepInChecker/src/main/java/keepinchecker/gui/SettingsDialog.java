package keepinchecker.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import net.miginfocom.swing.MigLayout;

public class SettingsDialog {

	private JFrame dialogFrame;
	
	private JLabel nameLabel;
	private JTextField nameTextField;
	
	private JLabel emailLabel;
	private JTextField emailTextField;
	
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	
	private JLabel partnersEmailsLabel;
	private JScrollPane scrollPane;
	private JTable partnersEmailsTable;
	
	private JLabel emailFrequencyLabel;
	private JComboBox<String> emailFrequencyCombo;
	
	private JButton saveButton;
	private JButton cancelButton;

	public SettingsDialog() {
		initialize();
	}
	
	public void open() {
		dialogFrame.setVisible(true);
	}

	private void initialize() {
		dialogFrame = new JFrame();
		dialogFrame.setTitle("Settings");
		dialogFrame.setBounds(100, 100, 450, 300);
		dialogFrame.getContentPane().setLayout(new MigLayout("", "[][][grow]", "[][][][grow][][]"));
		
		nameLabel = new JLabel("Name:");
		dialogFrame.getContentPane().add(nameLabel, "cell 0 0");
		
		nameTextField = new JTextField();
		dialogFrame.getContentPane().add(nameTextField, "cell 2 0,growx");
		nameTextField.setColumns(10);
		
		emailLabel = new JLabel("Email:");
		dialogFrame.getContentPane().add(emailLabel, "cell 0 1");
		
		emailTextField = new JTextField();
		dialogFrame.getContentPane().add(emailTextField, "cell 2 1,growx");
		emailTextField.setColumns(10);
		
		passwordLabel = new JLabel("Password:");
		dialogFrame.getContentPane().add(passwordLabel, "cell 0 2");
		
		passwordField = new JPasswordField();
		dialogFrame.getContentPane().add(passwordField, "cell 2 2,growx");
		
		partnersEmailsLabel = new JLabel("Partners' Emails:");
		dialogFrame.getContentPane().add(partnersEmailsLabel, "cell 0 3");
		
		scrollPane = new JScrollPane();
		dialogFrame.getContentPane().add(scrollPane, "cell 2 3,grow");
		
		partnersEmailsTable = new JTable(new PartnersEmailsTableModel());
		scrollPane.setViewportView(partnersEmailsTable);
		
		emailFrequencyLabel = new JLabel("Email Frequency:");
		dialogFrame.getContentPane().add(emailFrequencyLabel, "cell 0 4");
		
		emailFrequencyCombo = new JComboBox<>();
		emailFrequencyCombo.addItem("Daily");
		emailFrequencyCombo.addItem("Weekly");
		dialogFrame.getContentPane().add(emailFrequencyCombo, "cell 2 4,alignx left");
		
		saveButton = new JButton("Save");
		dialogFrame.getContentPane().add(saveButton, "flowx,cell 2 5");
		
		cancelButton = new JButton("Cancel");
		dialogFrame.getContentPane().add(cancelButton, "cell 2 5");
	}
	
    private class PartnersEmailsTableModel extends AbstractTableModel {
    	
		private static final long serialVersionUID = -3807071850727921483L;

		private String[] columnNames = { "Email Addresses:" };
		private String data[][] = new String[1][10];
		
		@Override
		public int getRowCount() {
		    return data.length;
		}
		
		@Override
		public int getColumnCount() {
		    return columnNames.length;
		}
		
		@Override
		public String getColumnName(int column) {
		    return columnNames[column];
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
		    return data[rowIndex][columnIndex];
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
		    return true;
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
		    data[rowIndex][columnIndex] = value.toString();
		    fireTableCellUpdated(rowIndex, columnIndex);
		}
	
    }
    
}
