package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import control.CreatePropertyController;
import entity.PropertyListing;

public class CreatePropertyUI extends JDialog {
	private JTextField locationField, typeField, pricingField, sellerIdField, agentIdField, statusField;
	private JTextArea descriptionArea;
	private JButton createButton, uploadButton;
	private JLabel photoLabel;
	private File selectedPhotoFile;
	private String filename;

	public CreatePropertyUI(Frame owner, boolean modal) {
		super(owner, modal);
		setTitle("Create New Property");
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		// Photo Upload Panel
		JPanel photoPanel = new JPanel();
		photoPanel.setBorder(BorderFactory.createTitledBorder("Property Photo"));
		uploadButton = new JButton("Upload Photo");
		photoLabel = new JLabel();
		photoLabel.setPreferredSize(new Dimension(100, 100));
		photoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		uploadButton.addActionListener(this::uploadPhotoAction);
		photoPanel.add(uploadButton);
		photoPanel.add(photoLabel);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(photoPanel, constraints);

		// Property Details Panel
		JPanel detailsPanel = new JPanel(new GridBagLayout());
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Property Details"));
		locationField = new JTextField(20);
		typeField = new JTextField(20);
		pricingField = new JTextField(20);
		sellerIdField = new JTextField(20);
		agentIdField = new JTextField(20);
		statusField = new JTextField(20);
		descriptionArea = new JTextArea(3, 20);
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);

		addComponent(detailsPanel, new JLabel("Seller ID:"), 0, 0);
		addComponent(detailsPanel, sellerIdField, 1, 0);
		addComponent(detailsPanel, new JLabel("Agent ID:"), 0, 1);
		addComponent(detailsPanel, agentIdField, 1, 1);
		addComponent(detailsPanel, new JLabel("Type:"), 0, 2);
		addComponent(detailsPanel, typeField, 1, 2);
		addComponent(detailsPanel, new JLabel("Location:"), 0, 3);
		addComponent(detailsPanel, locationField, 1, 3);
		addComponent(detailsPanel, new JLabel("Pricing:"), 0, 4);
		addComponent(detailsPanel, pricingField, 1, 4);
		addComponent(detailsPanel, new JLabel("Status:"), 0, 5);
		addComponent(detailsPanel, statusField, 1, 5);
		addComponent(detailsPanel, new JLabel("Description:"), 0, 6);
		addComponent(detailsPanel, new JScrollPane(descriptionArea), 1, 6);

		constraints.gridy = 1;
		getContentPane().add(detailsPanel, constraints);

		// Create Button
		createButton = new JButton("Create Property");
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		getContentPane().add(createButton, constraints);
		setupCreatePropertyButton();

		pack();
		setLocationRelativeTo(owner);
	}

	private void addComponent(JPanel panel, JComponent component, int x, int y) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 5, 5, 5);
		panel.add(component, constraints);
	}

	private void uploadPhotoAction(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg"));
		int option = fileChooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			selectedPhotoFile = fileChooser.getSelectedFile(); // Store the selected file
			try {
				BufferedImage image = ImageIO.read(selectedPhotoFile);
				ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
				photoLabel.setIcon(icon);
				saveImage(selectedPhotoFile); // Save the selected image
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void saveImage(File imageFile) {
		File directory = new File("property_images");
		if (!directory.exists()) {
			directory.mkdirs(); // Create the directory if it does not exist
		}

		// Generate a unique filename using datetime and a random UUID
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String randomUUID = UUID.randomUUID().toString().substring(0, 8);
		filename = timestamp + "_" + randomUUID + ".png";

		File destination = new File(directory, filename);
		try {
			BufferedImage image = ImageIO.read(imageFile);
			if (image == null) {
				System.out.println("Failed to read image file.");
				return;
			}
			ImageIO.write(image, "png", destination);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving image: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void setupCreatePropertyButton() {
		createButton.addActionListener((ActionEvent e) -> {
			String sellerid = sellerIdField.getText();
			String agentid = agentIdField.getText();
			String type = typeField.getText();
			String location = locationField.getText();
			String pricing = pricingField.getText();
			String status = statusField.getText();
			String description = descriptionArea.getText();
			String photo = filename != null ? filename : "";

			PropertyListing propertyListing = new PropertyListing();
			CreatePropertyController controller = new CreatePropertyController(propertyListing);
			String result = controller.createNewProfile(sellerid, agentid, photo, location, description, type, pricing,
					status);
			JOptionPane.showMessageDialog(this, result);
			if (result.equals("Property created successfully.")) {
				dispose();
			}
		});
	}
}
