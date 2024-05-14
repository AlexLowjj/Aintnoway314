package boundary;

import entity.UserAccount;
import control.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginUI extends JFrame {
	private JTextField emailField = new JTextField(15);
	private JPasswordField passwordField = new JPasswordField(15);
	private JButton btnLogin = new JButton("Login");
	private JLabel resetPasswordLabel = new JLabel("<html><u>Reset Password</u></html>");

	public LoginUI() {
		super("Login");
		layoutComponents();
		setupLoginButton();
		setupResetPassword();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void layoutComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(10, 10, 10, 10);

		addComponent(new JLabel("Email:"), 0, 0);
		addComponent(emailField, 1, 0);
		addComponent(new JLabel("Password:"), 0, 1);
		addComponent(passwordField, 1, 1);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.CENTER;
		add(btnLogin, constraints);

		constraints.gridy = 4;
		constraints.insets = new Insets(20, 10, 10, 10);
		add(resetPasswordLabel, constraints);
	}

	private void setupLoginButton() {
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailField.getText();
				String password = new String(passwordField.getPassword());

				UserAccount userAccount = new UserAccount(email, password);
				LoginController loginController = new LoginController(userAccount);
				String response = loginController.authenticate(email, password);

				if (!response.equals("login problem") && !response.equals("db connect problem")
						&& !response.equals("No user found with the given credentials.")) {
					if (response.equals("User account is suspended.")) {
						JOptionPane.showMessageDialog(LoginUI.this,
								"Your account is suspended, please contact the admin.", "Account Suspended",
								JOptionPane.WARNING_MESSAGE);
					} else {
						// split into id and type
						String[] idAndType = response.split("-");
						int userId = Integer.parseInt(idAndType[0]);
						String type = idAndType[1];

						switch (type) {
						case "Admin":
							AdminUI adminUI = new AdminUI();
							adminUI.setVisible(true);
							dispose();
							break;
						case "Agent":
							AgentUI agentUI = new AgentUI(userId);
							agentUI.setVisible(true);
							dispose();
							break;
						case "Seller":
							SellerMenuUI sellerUI = new SellerMenuUI(userId);
							dispose();
							break;
						default:
							JOptionPane.showMessageDialog(LoginUI.this,
									"Please check with developer about the usertype", "Wrong UserType",
									JOptionPane.WARNING_MESSAGE);
							break;
						}
					}
				} else {
					JOptionPane.showMessageDialog(LoginUI.this, response, "Login Failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void setupResetPassword() {
		resetPasswordLabel.setForeground(Color.BLUE);
		resetPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		resetPasswordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ResetPasswordUI resetPasswordUI = new ResetPasswordUI();
				resetPasswordUI.setVisible(true);

//                }
			}
		});
	}

	private void addComponent(Component component, int x, int y) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(component, constraints);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new LoginUI());
	}
}
