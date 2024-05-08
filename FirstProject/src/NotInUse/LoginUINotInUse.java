package NotInUse;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import boundary.HyperLinkButtonUI;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LoginUINotInUse extends JFrame {
    private Icon houseImage;
    private JLabel houseLabel;

    private JPanel loginPanel;
    private JLabel userProfileLabel;
    private JComboBox userProfileBox;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton confirmButton;

    private HyperLinkButtonUI registerButton;

    public LoginUINotInUse() {
        // set title
        super("User Verification");
        // set basic settings for the main frame
        setSize(550, 460);
        setResizable(false);
        setLocationRelativeTo(null); // center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create image label
        houseImage = new ImageIcon("./src/boundary/img/login_house.jpg");
        houseLabel = new JLabel(houseImage);

        // create login panel
        loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // create components for drop down list
        userProfileLabel = new JLabel("Role:");
        String[] userProfiles = {"admin", "agent", "seller", "buyer"};
        userProfileBox = new JComboBox(userProfiles);

        // create components for id and password
        emailLabel = new JLabel("Email:");
        emailField = new JTextField(10);

        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(10);

        // create confirm button
        confirmButton = new JButton("confirm");

        // create register button
        registerButton = new HyperLinkButtonUI("Don't have an account? Click here.", new Color(35, 118, 181));

        // add components to login panel
        loginPanel.add(userProfileLabel);
        loginPanel.add(userProfileBox);
        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(confirmButton);

        // set layout and add components to the main frame
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(houseLabel);
        add(loginPanel);
        add(registerButton);

        // event handler
        ButtonHandler handler = new ButtonHandler();
        confirmButton.addActionListener(handler);
        registerButton.addActionListener(handler);

        setVisible(true); // show the window
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == confirmButton) {

            	UserNotInUse user = LoginControllerNotInUse.authenticate(emailField.getText(), new String(passwordField.getPassword()));

                // set user profile
                String role = String.valueOf(userProfileBox.getSelectedItem());

                if (user.getEmail() != null) {
                	LoginUINotInUse.this.dispose();
                	// show prompt
                    JOptionPane.showMessageDialog(LoginUINotInUse.this, "Login successful", "message",
                            JOptionPane.INFORMATION_MESSAGE);
                    switch (role){
                    	case "admin":
                    		break;
                    	case "agent":
                    		break;
                    	case "seller":
                    		break;
                    	case "buyer":
                    		break;
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginUINotInUse.this, "Login failed", "message",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (e.getSource() == registerButton) {

            }
        }
    }
}