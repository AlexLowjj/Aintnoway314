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

import control.GetPropertyByCharController;
import control.UpdatePropertyController;
import control.UpdateUserController;
import entity.PropertyListing;
import entity.UserAccount;

public class UpdatePropertyUI extends JDialog {
	private JTextField typeField, locationField, descriptionField, pricingField, statusField, sellerIdField,
			agentIdField;
	private JLabel imageLabel;
	private JButton updateButton, uploadButton;
	private String propertyID;
	private String photoPath;
	String propertyIdHolder;
	private File selectedImageFile;
	private PropertyListing propertyListing;

	public UpdatePropertyUI(Frame owner, boolean modal, String propertyID) {
		super(owner, modal);
		this.propertyID = propertyID;
		setTitle("Update Property");
		getContentPane().setLayout(new GridBagLayout());
		initializeComponents();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(owner);
		loadData();
	}

	private void initializeComponents() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5);
		initializePhotoPanel(constraints);
		initializeDetailsPanel(constraints);
		initializeUpdateButton(constraints);
		setupUpdatePropertyButton();
	}

	private void initializePhotoPanel(GridBagConstraints constraints) {
		JPanel photoPanel = new JPanel();
		photoPanel.setBorder(BorderFactory.createTitledBorder("Property Photo"));
		imageLabel = new JLabel();
		imageLabel.setPreferredSize(new Dimension(100, 100));
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		uploadButton = new JButton("Upload New Image");

		photoPanel.add(uploadButton);
		photoPanel.add(imageLabel);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(photoPanel, constraints);

	}

	private void initializeDetailsPanel(GridBagConstraints constraints) {
		JPanel detailsPanel = new JPanel(new GridBagLayout());
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Property Details"));

		typeField = new JTextField(20);
		locationField = new JTextField(20);
		descriptionField = new JTextField(20);
		pricingField = new JTextField(20);
		statusField = new JTextField(20);
		sellerIdField = new JTextField(20);
		sellerIdField.setEditable(false);

		agentIdField = new JTextField(20);
		agentIdField.setEditable(false);

		String[] labels = { "Type:", "Location:", "Description:", "Pricing:", "Status:", "Seller ID:", "Agent ID:" };
		JTextField[] fields = { typeField, locationField, descriptionField, pricingField, statusField, sellerIdField,
				agentIdField };
		for (int i = 0; i < labels.length; i++) {
			addComponent(detailsPanel, new JLabel(labels[i]), fields[i], 0, i);
		}

		constraints.gridy = 1;
		getContentPane().add(detailsPanel, constraints);

	}

	private void addComponent(JPanel panel, JLabel label, JTextField field, int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		panel.add(label, gbc);
		gbc.gridx = 1;
		panel.add(field, gbc);
	}

	private void initializeUpdateButton(GridBagConstraints constraints) {
		updateButton = new JButton("Update Property");
		constraints.gridy = 2;
		getContentPane().add(updateButton, constraints);
	}

	private void setupUpdatePropertyButton() {
		updateButton.addActionListener(e -> {
			System.out.println("Updating user...");
			if (selectedImageFile != null) {
				saveImage(selectedImageFile);
			}

			String type = typeField.getText();
			String location = locationField.getText();
			String description = descriptionField.getText();
			String pricing = pricingField.getText();
			String status = statusField.getText();
			String filename = photoPath;

			PropertyListing propertyListing = new PropertyListing(type, location, description, pricing, status,
					filename, propertyIdHolder);
			UpdatePropertyController updatePropertyController = new UpdatePropertyController(propertyListing);
			String updatePropertyStatus = updatePropertyController.updateProperty(type, location, description, pricing,
					status, filename, propertyIdHolder);
			System.out.println(updatePropertyStatus);
			if (updatePropertyStatus.contains("successfully")) {
				JOptionPane.showMessageDialog(UpdatePropertyUI.this, updatePropertyStatus);
				dispose();
			} else {
				JOptionPane.showMessageDialog(UpdatePropertyUI.this, "Updated was failed");
			}

		});
	}

//    private void updatePropertyAction(ActionEvent e) {
//        // Here we would handle property updates similarly to user updates in UpdateUserUI
//        // Assuming we have a method in PropertyListing or a control class for updating properties
//    }

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
		photoPath = timestamp + "_" + randomUUID + ".png";

		File destination = new File(directory, photoPath);
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
				File file = new File("property_images/" + filename);
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

	private void loadData() {
		// Load the property details similarly to how user details are loaded in
		// UpdateUserUI
		String[] parts = propertyID.split("-");
		String typeParts = parts[0];
		String locationParts = parts[1];
		String pricingParts = parts[2];

		GetPropertyByCharController getPropertyByChar = new GetPropertyByCharController(new PropertyListing());
		List<String> propertyDetails = getPropertyByChar.getPropertyByChar(typeParts, locationParts, pricingParts);

		if (!propertyDetails.isEmpty()) {
			typeField.setText(propertyDetails.get(3));
			locationField.setText(propertyDetails.get(1));
			descriptionField.setText(propertyDetails.get(2));
			pricingField.setText(propertyDetails.get(4));
			statusField.setText(propertyDetails.get(5));
			sellerIdField.setText(propertyDetails.get(6));
			agentIdField.setText(propertyDetails.get(7));
			propertyIdHolder = propertyDetails.get(8);
			// and so on for other fields
			photoPath = propertyDetails.get(0);
			displayImage(photoPath);
		}
	}

	public static void main(String[] args) {
		UpdatePropertyUI dialog = new UpdatePropertyUI(new JFrame(), true, "House-123 Main St-200");
		dialog.setVisible(true);
	}
}
