package boundary;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import control.GetProfileInfoByIDController;
import control.UpdateProfileController;
import entity.UserProfile;

public class UpdateProfileUI extends JDialog {
	private JTextField pdidField, nameField;
	private JTextArea descriptionArea;
	private JComboBox<String> isSuspendedCombo;
	private JButton updateButton;
	private String pdid;

	public UpdateProfileUI(Frame owner, boolean modal, String pdid) {
		super(owner, modal);
		this.pdid = pdid;
//        this.adminController = new AdminController(new Admin()); // Ensure you have a suitable constructor in Admin
		setTitle("Update Profile");
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);

		// Role Information Panel
		JPanel rolePanel = new JPanel(new GridBagLayout());
		rolePanel.setBorder(BorderFactory.createTitledBorder("Role Information"));

		pdidField = new JTextField(20);
		pdidField.setEditable(false);
		nameField = new JTextField(20);
		descriptionArea = new JTextArea(3, 20);
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		isSuspendedCombo = new JComboBox<>(new String[] { "Yes", "No" });

		addComponent(rolePanel, new JLabel("PDID:"), 0, 0);
		addComponent(rolePanel, pdidField, 1, 0);
		addComponent(rolePanel, new JLabel("Name:"), 0, 1);
		addComponent(rolePanel, nameField, 1, 1);
		addComponent(rolePanel, new JLabel("Description:"), 0, 2);
		addComponent(rolePanel, new JScrollPane(descriptionArea), 1, 2);
		addComponent(rolePanel, new JLabel("Is Suspended:"), 0, 3);
		addComponent(rolePanel, isSuspendedCombo, 1, 3);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(rolePanel, constraints);

		// Update Button
		updateButton = new JButton("Update Profile");
		updateButton.addActionListener(e -> updateRoleAction());
		constraints.gridy = 1;
		getContentPane().add(updateButton, constraints);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(owner);
		loadData();
	}

	private void addComponent(JPanel panel, JComponent component, int x, int y) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 5, 5, 5);
		panel.add(component, constraints);
	}

	private void loadData() {
		UserProfile userProfile = new UserProfile(pdid);
		GetProfileInfoByIDController getProfileInfoByID = new GetProfileInfoByIDController(userProfile);
		List<String> roleInfo = getProfileInfoByID.getProfileByID(pdid);
		System.out.println(roleInfo);

		if (roleInfo != null && !roleInfo.isEmpty()) {
			pdidField.setText(roleInfo.get(0));
			nameField.setText(roleInfo.get(1));
			descriptionArea.setText(roleInfo.get(2));
			isSuspendedCombo.setSelectedItem(roleInfo.get(3).equals("f") ? "No" : "Yes");
		}
	}

	private void updateRoleAction() {
		int pdid = Integer.parseInt(pdidField.getText());
		String name = nameField.getText();
		String description = descriptionArea.getText();
		String is_suspendedTemp = (String) isSuspendedCombo.getSelectedItem();
		boolean is_suspended;
		if (is_suspendedTemp.equals("Yes")) {
			is_suspended = Boolean.parseBoolean("True");
		} else {
			is_suspended = Boolean.parseBoolean("False");
		}

		UserProfile userProfile = new UserProfile(name, description, pdid, is_suspended);
		UpdateProfileController updateProfileController = new UpdateProfileController(userProfile);
		boolean updateProfileStatus = updateProfileController.updateProfile(name, description, pdid, is_suspended);

		if (updateProfileStatus) {
			JOptionPane.showMessageDialog(UpdateProfileUI.this, "Profile had updated successfully!!");
			dispose();
		} else {
			JOptionPane.showMessageDialog(UpdateProfileUI.this, "Profile had failed to updated");

		}

	}
}
