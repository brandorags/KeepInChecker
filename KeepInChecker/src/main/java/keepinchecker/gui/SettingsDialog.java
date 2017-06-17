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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import keepinchecker.constants.Constants;
import keepinchecker.database.entity.User;
import keepinchecker.database.manager.UserManager;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

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
	
	private final User user = new User();

	public SettingsDialog() {
		boolean isUserNotNull = true;
		if (Constants.USER == null) {
			isUserNotNull = false;
		}
		
		// we should only ever have one user, so set its ID to 1 by default
		user.setUserId(isUserNotNull ? Constants.USER.getUserId() : 1);
		
		user.setUserName(isUserNotNull ? Constants.USER.getUserName() : new byte[0]);
		user.setUserEmail(isUserNotNull ? Constants.USER.getUserEmail() : new byte[0]);
		user.setUserEmailPassword(isUserNotNull ? Constants.USER.getUserEmailPassword() : new byte[0]);
		user.setPartnerEmails(isUserNotNull ? Constants.USER.getPartnerEmails() : new ArrayList<>());
		user.setEmailFrequency(isUserNotNull ? Constants.USER.getEmailFrequency() : "");

		init();
	}
	
	public void open() {
		dialogFrame.setVisible(true);
	}

	private void init() {
		dialogFrame = new JFrame();
		dialogFrame.setTitle("Settings");
		dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialogFrame.setBounds(100, 100, 450, 300);
		dialogFrame.getContentPane().setLayout(new MigLayout("", "[][][grow]", "[][][][grow][][]"));
		dialogFrame.setLocationRelativeTo(null);
		
		nameLabel = new JLabel("Name:");
		dialogFrame.getContentPane().add(nameLabel, "cell 0 0");
		
		nameTextField = new JTextField();
		dialogFrame.getContentPane().add(nameTextField, "cell 2 0,growx");
		nameTextField.setColumns(10);
		nameTextField.setText(new String(user.getUserName(), StandardCharsets.UTF_8));
		
		emailLabel = new JLabel("Email:");
		dialogFrame.getContentPane().add(emailLabel, "cell 0 1");
		
		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		emailTextField.setText(new String(user.getUserEmail(), StandardCharsets.UTF_8));
		dialogFrame.getContentPane().add(emailTextField, "cell 2 1,growx");
		
		passwordLabel = new JLabel("Password:");
		dialogFrame.getContentPane().add(passwordLabel, "cell 0 2");
		
		passwordField = new JPasswordField();
		passwordField.setText(new String(user.getUserEmailPassword(), StandardCharsets.UTF_8));
		dialogFrame.getContentPane().add(passwordField, "cell 2 2,growx");
		
		partnersEmailsLabel = new JLabel("Partners' Emails:");
		dialogFrame.getContentPane().add(partnersEmailsLabel, "cell 0 3");
		
		scrollPane = new JScrollPane();
		dialogFrame.getContentPane().add(scrollPane, "cell 2 3,grow");
		
		partnersEmailsTable = new JTable(new DefaultTableModel(new String[] { "Email Addresses:" }, 10));
		((DefaultTableCellRenderer) partnersEmailsTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < user.getPartnerEmails().size(); i++) {
			String partnerEmail = new String(user.getPartnerEmails().get(i), StandardCharsets.UTF_8);
			partnersEmailsTable.getModel().setValueAt(partnerEmail, i, 0);
		}
		scrollPane.setViewportView(partnersEmailsTable);
		
		emailFrequencyLabel = new JLabel("Email Frequency:");
		dialogFrame.getContentPane().add(emailFrequencyLabel, "cell 0 4");
		
		emailFrequencyCombo = new JComboBox<>();
		emailFrequencyCombo.addItem(User.EMAIL_FREQUENCY_DAILY);
		emailFrequencyCombo.addItem(User.EMAIL_FREQUENCY_WEEKLY);
		emailFrequencyCombo.setSelectedItem(user.getEmailFrequency());
		dialogFrame.getContentPane().add(emailFrequencyCombo, "cell 2 4,alignx left");
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = nameTextField.getText();
				String userEmail = emailTextField.getText();
				String userEmailPassword = new String(passwordField.getPassword());
				String emailFrequency = emailFrequencyCombo.getSelectedItem().toString();
				ArrayList<byte[]> partnerEmails = new ArrayList<>();
				for (int i = 0; i < partnersEmailsTable.getModel().getRowCount(); i++) {
					try {
						String partnerEmail = partnersEmailsTable.getModel().getValueAt(i, 0).toString();
						if (partnerEmail != null) {						
							partnerEmails.add(partnerEmail.getBytes(StandardCharsets.UTF_8));
						}
					} catch (Exception ex) {
						// it's an empty cell so keep going
						continue;
					}
				}
				
				try {
					user.setUserName(userName.getBytes(StandardCharsets.UTF_8));
					user.setUserEmail(userEmail.getBytes(StandardCharsets.UTF_8));
					user.setUserEmailPassword(userEmailPassword.getBytes(StandardCharsets.UTF_8));
					user.setPartnerEmails(partnerEmails);
					user.setEmailFrequency(emailFrequency);
					
					UserManager userManager = new UserManager();
					userManager.saveUser(user);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				dialogFrame.dispatchEvent(new WindowEvent(dialogFrame, WindowEvent.WINDOW_CLOSING));
			}
		});
		dialogFrame.getContentPane().add(saveButton, "flowx,cell 2 5");
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogFrame.dispatchEvent(new WindowEvent(dialogFrame, WindowEvent.WINDOW_CLOSING));
			}
		});
		dialogFrame.getContentPane().add(cancelButton, "cell 2 5");
	}
	
}
