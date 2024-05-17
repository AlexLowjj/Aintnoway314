package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import control.GetTypeOfProfileController;
import control.GetUserInfoByIDController;
import control.UpdateUserController;
import entity.UserAccount;
import entity.UserProfile;

public class UpdateUserUI extends JDialog {
	private JTextField usernameField, emailField, nameField, phoneField, userIDField;
	private JPasswordField passwordField;
	private JTextArea descriptionArea;
	private JButton updateButton, uploadButton;
	private JComboBox<String> userTypeCombo, isSuspendedCombo;
	private JLabel imageLabel;
	private String userID;
	private String imagePath;
	private File selectedImageFile;

	public UpdateUserUI(Frame owner, boolean modal, String userID) {
		super(owner, modal);
		this.userID = userID;
//        this.adminController = new AdminController(new Admin(userID));
		setTitle("Update User");
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);

		// Image Upload Panel
		JPanel imagePanel = new JPanel();
		imagePanel.setBorder(BorderFactory.createTitledBorder("Profile Image"));

		uploadButton = new JButton("Upload New Image");
		imageLabel = new JLabel();
		imageLabel.setPreferredSize(new Dimension(100, 100));
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		uploadButton.addActionListener(e -> uploadImageAction());
		imagePanel.add(uploadButton);
		imagePanel.add(imageLabel);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(imagePanel, constraints);

		// User Account Panel
		JPanel accountPanel = new JPanel(new GridBagLayout());
		accountPanel.setBorder(BorderFactory.createTitledBorder("User Account"));

		userTypeCombo = new JComboBox<>();
		isSuspendedCombo = new JComboBox<>(new String[] { "Yes", "No" });
		userIDField = new JTextField(20);
		userIDField.setEditable(false);
		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);
		emailField = new JTextField(20);

		// Call to the controller to get user types from the database

		UserProfile userProfile = new UserProfile();
		GetTypeOfProfileController getTypeOfProfileController = new GetTypeOfProfileController(userProfile);
		List<String> userTypes = getTypeOfProfileController.getUserTypes();

		for (String userType : userTypes) {
			userTypeCombo.addItem(userType);
		}

		addComponent(accountPanel, new JLabel("User ID:"), 0, 0);
		addComponent(accountPanel, userIDField, 1, 0);
		addComponent(accountPanel, new JLabel("Username:"), 0, 1);
		addComponent(accountPanel, usernameField, 1, 1);
		addComponent(accountPanel, new JLabel("Password:"), 0, 2);
		addComponent(accountPanel, passwordField, 1, 2);
		addComponent(accountPanel, new JLabel("Email:"), 0, 3);
		addComponent(accountPanel, emailField, 1, 3);
		addComponent(accountPanel, new JLabel("User Type:"), 0, 4);
		addComponent(accountPanel, userTypeCombo, 1, 4);
		addComponent(accountPanel, new JLabel("Is Suspended:"), 0, 5);
		addComponent(accountPanel, isSuspendedCombo, 1, 5);

		constraints.gridy = 1;
		getContentPane().add(accountPanel, constraints);

		// Personal Information Panel
		JPanel personalPanel = new JPanel(new GridBagLayout());
		personalPanel.setBorder(BorderFactory.createTitledBorder("Personal Information"));
		nameField = new JTextField(20);
		descriptionArea = new JTextArea(3, 20);
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		phoneField = new JTextField(20);

		addComponent(personalPanel, new JLabel("Name:"), 0, 0);
		addComponent(personalPanel, nameField, 1, 0);
		addComponent(personalPanel, new JLabel("Description:"), 0, 1);
		addComponent(personalPanel, new JScrollPane(descriptionArea), 1, 1);
		addComponent(personalPanel, new JLabel("Phone:"), 0, 2);
		addComponent(personalPanel, phoneField, 1, 2);

		constraints.gridy = 2;
		getContentPane().add(personalPanel, constraints);

		// Update Button
		updateButton = new JButton("Update User");
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		getContentPane().add(updateButton, constraints);

		setupUpdateUserButton();

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

	private void uploadImageAction() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg"));
		int option = fileChooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			selectedImageFile = fileChooser.getSelectedFile(); // Store the selected file
			try {
				ImageIcon icon = new ImageIcon(ImageIO.read(selectedImageFile));
				Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(image));
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void saveImage(File imageFile) {
		File directory = new File("images");
		if (!directory.exists()) {
			directory.mkdirs(); // Create the directory if it does not exist
		}

		// Generate a unique filename using datetime and a random UUID
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String randomUUID = UUID.randomUUID().toString().substring(0, 8);
		imagePath = timestamp + "_" + randomUUID + ".png";

		File destination = new File(directory, imagePath);
		try {
			BufferedImage image = ImageIO.read(imageFile);
			if (image == null) {
				System.out.println("Failed to read image file.");
				return;
			}
			boolean result = ImageIO.write(image, "png", destination);
			System.out.println("Image saved: " + result + ", Path: " + destination.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving image: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void displayImage(String filename) {
		if (filename != null && !filename.isEmpty()) {
			try {
				File file = new File("images/" + filename);
				if (file.exists()) {
					Image image = ImageIO.read(file);
					ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
					imageLabel.setIcon(icon);
				}
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void setupUpdateUserButton() {
		updateButton.addActionListener(e -> {
			System.out.println("Updating user...");
			if (selectedImageFile != null) {
				saveImage(selectedImageFile);
			}

			int userid = Integer.parseInt(userIDField.getText());
			String username = usernameField.getText();
			String email = emailField.getText();
			String name = nameField.getText();
			String phone = phoneField.getText();
			String description = descriptionArea.getText();
			String password = new String(passwordField.getPassword());
			String userType = (String) userTypeCombo.getSelectedItem();
			String is_suspendedTemp = (String) isSuspendedCombo.getSelectedItem();
			String filename = imagePath;
			boolean is_suspended;
			if (is_suspendedTemp.equals("Yes")) {
				is_suspended = Boolean.parseBoolean("True");
			} else {
				is_suspended = Boolean.parseBoolean("False");
			}

			UserAccount userAccount = new UserAccount(username, password, email, is_suspended, userType, userid, name,
					description, filename, phone);
			UpdateUserController updateUserController = new UpdateUserController(userAccount);
			String updateUserStatus = updateUserController.updateUser(username, password, email, is_suspended, userType,
					userid, name, description, filename, phone);

			if (updateUserStatus.contains("already exists")) {
				JOptionPane.showMessageDialog(UpdateUserUI.this, updateUserStatus);
			} else {
				JOptionPane.showMessageDialog(UpdateUserUI.this, updateUserStatus);
				dispose();
			}

		});
	}

	private void loadData() {
		UserAccount userAccount = new UserAccount(userID);
		GetUserInfoByIDController getUserInfoByID = new GetUserInfoByIDController(userAccount);
		List<String> userInfo = getUserInfoByID.getUserInfoByID(userID);

		if (userInfo != null && !userInfo.isEmpty()) {
			userIDField.setText(userInfo.get(0));
			usernameField.setText(userInfo.get(1));
			passwordField.setText(userInfo.get(2));
			emailField.setText(userInfo.get(3));
			isSuspendedCombo.setSelectedItem(userInfo.get(4).equals("f") ? "No" : "Yes");
			userTypeCombo.setSelectedItem(userInfo.get(5));
			nameField.setText(userInfo.get(6));
			descriptionArea.setText(userInfo.get(7));
			phoneField.setText(userInfo.get(9));
			imagePath = userInfo.get(8);
			displayImage(imagePath);
		}
	}

	public static void main(String[] args) {
		UpdateUserUI dialog = new UpdateUserUI(new JFrame(), true, "33"); // Example user ID
		dialog.setVisible(true);
	}
}
