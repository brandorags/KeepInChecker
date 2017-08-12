package keepinchecker.gui;

import javax.swing.*;
import java.awt.event.*;

public class WelcomeDialog extends JDialog {

    private JPanel loginDialogContentPane;
    private JButton buttonOK;
    private JButton closeButton;
    private JTextArea welcomeTextArea;
    private JButton sendEmailButton;
    private JTextField emailAddressTextField;
    private JLabel sentConfirmLabel;

    public WelcomeDialog() {
        setContentPane(loginDialogContentPane);
        setModal(true);
        setTitle("KeepInChecker");
        setBounds(100, 100, 450, 300);
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(closeButton);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });

        // call onClose() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        // call onClose() on ESCAPE
        loginDialogContentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void open() {
        setVisible(true);
    }

    private void onClose() {
        dispose();
    }

}
