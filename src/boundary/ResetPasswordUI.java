package boundary;

import javax.swing.*;

import control.ResetPasswordController;
import entity.UserAccount;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResetPasswordUI extends JFrame {
	private JTextField textEmail = new JTextField(15);
	private JPasswordField textOldPassword = new JPasswordField(15);
	private JPasswordField textNewPassword = new JPasswordField(15);
	private JButton btnReset = new JButton("Reset Password");

	public ResetPasswordUI() {
		super("Reset Password");
		layoutComponents();
		setupResetButton();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void layoutComponents() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(10, 10, 10, 10);

		addComponent(panel, new JLabel("Email:"), 0, 0);
		addComponent(panel, textEmail, 1, 0);
		addComponent(panel, new JLabel("Old Password:"), 0, 1);
		addComponent(panel, textOldPassword, 1, 1);
		addComponent(panel, new JLabel("New Password:"), 0, 2);
		addComponent(panel, textNewPassword, 1, 2);

		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(btnReset, constraints);

		setContentPane(panel);
	}

	private void setupResetButton() {
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = textEmail.getText();
				String oldPassword = new String(textOldPassword.getPassword());
				String newPassword = new String(textNewPassword.getPassword());

				UserAccount userAccount = new UserAccount(email, oldPassword, newPassword);
				ResetPasswordController resetPasswordController = new ResetPasswordController(userAccount);
				boolean response = resetPasswordController.resetPassword(email, oldPassword, newPassword);
				if (response) {
					JOptionPane.showMessageDialog(ResetPasswordUI.this, "Password Reset Successfully", "Reset Password",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					JOptionPane.showMessageDialog(ResetPasswordUI.this,
							"Password reset failed, please check your email or password", "Reset Password",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	private void addComponent(JPanel panel, Component component, int x, int y) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(10, 10, 10, 10);
		panel.add(component, constraints);
	}
}
