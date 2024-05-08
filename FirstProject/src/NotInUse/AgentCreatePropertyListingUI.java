package NotInUse;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.io.File;

import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;

import NotInUse.AgentCreatePropertyListingController;

public class AgentCreatePropertyListingUI extends JFrame {

	private int userId;

	private JLabel sellerIdLabel;
	private JTextField sellerIdTextField;

	private JLabel purposeLabel;
	private JRadioButton rentRadioButton;
	private JRadioButton saleRadioButton;
	private ButtonGroup purposeButtonGroup;

	private JLabel typeLabel;
	private JComboBox<String> typeComboBox;

	private JLabel priceLabel;
	private JTextField priceTextField;
	private JLabel dollarSignLabel;

	private JLabel photoLabel;
	private JButton photoButton;
	private JLabel selectedPhotoLabel;

	private JLabel addressLabel;
	private JTextArea addressTextArea;

	private JLabel descriptionLabel;
	private JTextArea descriptionTextArea;

	private JButton createButton;

	private String photoFileName;

	// userId is passed in as a parameter
	// x and y is the location of the menu window
	// it's used to set the location
	public AgentCreatePropertyListingUI(int userId, int x, int y) {
		// set title
		super("Create New Property Listing");
		// set user id
		this.userId = userId;
		// set basic setting of the main frame
		setResizable(false);
		setBounds(x + 300, y - 150, 335, 580);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		// set and add components
		setComponents();
		addComponents();

		setVisible(true);
	}

	private void setComponents() {
		// sellerId
		sellerIdLabel = new JLabel("Seller ID:");
		sellerIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		sellerIdLabel.setBounds(25, 10, 63, 30);

		sellerIdTextField = new JTextField(15);
		sellerIdTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		sellerIdTextField.setBounds(107, 15, 63, 23);

		// purpose
		purposeLabel = new JLabel("Purpose:");
		purposeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		purposeLabel.setBounds(25, 50, 63, 30);

		rentRadioButton = new JRadioButton("Rent");
		rentRadioButton.setFont(new Font("Arial", Font.PLAIN, 14));
		rentRadioButton.setBounds(107, 55, 63, 23);

		saleRadioButton = new JRadioButton("Sale");
		saleRadioButton.setFont(new Font("Arial", Font.PLAIN, 14));
		saleRadioButton.setBounds(184, 55, 54, 23);

		purposeButtonGroup = new ButtonGroup();
		purposeButtonGroup.add(rentRadioButton);
		purposeButtonGroup.add(saleRadioButton);

		// type
		typeLabel = new JLabel("Type:");
		typeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		typeLabel.setBounds(25, 90, 54, 30);

		typeComboBox = new JComboBox<>(new String[] { "", "HDB", "Condo", "Apartment", "House" });
		typeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
		typeComboBox.setBounds(110, 95, 89, 23);

		// price
		priceLabel = new JLabel("Price:");
		priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		priceLabel.setBounds(25, 135, 54, 30);

		priceTextField = new JTextField();
		priceTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		priceTextField.setBounds(110, 140, 89, 21);
		priceTextField.setColumns(10);

		dollarSignLabel = new JLabel("S$");
		dollarSignLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		dollarSignLabel.setBounds(202, 140, 54, 15);

		// photo
		photoLabel = new JLabel("Photo:");
		photoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		photoLabel.setBounds(25, 180, 54, 30);

		photoButton = new JButton("Click to select file");
		photoButton.setFont(new Font("Arial", Font.PLAIN, 14));
		photoButton.setBounds(110, 185, 159, 23);

		selectedPhotoLabel = new JLabel();
		selectedPhotoLabel.setForeground(new Color(0, 128, 192));
		selectedPhotoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		selectedPhotoLabel.setBounds(25, 215, 285, 30);

		// address
		addressLabel = new JLabel("Address:");
		addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		addressLabel.setBounds(25, 250, 63, 30);

		addressTextArea = new JTextArea();
		addressTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		addressTextArea.setBounds(110, 255, 186, 75);
		addressTextArea.setLineWrap(true);

		// description
		descriptionLabel = new JLabel("Description:");
		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		descriptionLabel.setBounds(25, 335, 89, 30);

		descriptionTextArea = new JTextArea();
		descriptionTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		descriptionTextArea.setBounds(25, 370, 273, 102);
		descriptionTextArea.setLineWrap(true);

		// create button
		createButton = new JButton("Create");
		createButton.setFont(new Font("Arial", Font.PLAIN, 14));
		createButton.setBounds(125, 490, 80, 30);

		// -----------------
		// add event handler
		ButtonHandler bHandler = new ButtonHandler();
		photoButton.addActionListener(bHandler);
		createButton.addActionListener(bHandler);
	}

	private void addComponents() {
		// sellerId
		getContentPane().add(sellerIdLabel);
		getContentPane().add(sellerIdTextField);

		// purpose
		getContentPane().add(purposeLabel);
		getContentPane().add(rentRadioButton);
		getContentPane().add(saleRadioButton);

		// type
		getContentPane().add(typeLabel);
		getContentPane().add(typeComboBox);

		// price
		getContentPane().add(priceLabel);
		getContentPane().add(priceTextField);
		getContentPane().add(dollarSignLabel);

		// photo
		getContentPane().add(photoLabel);
		getContentPane().add(photoButton);
		getContentPane().add(selectedPhotoLabel);

		// address
		getContentPane().add(addressLabel);
		getContentPane().add(addressTextArea);

		// description
		getContentPane().add(descriptionLabel);
		getContentPane().add(descriptionTextArea);

		// create button
		getContentPane().add(createButton);
	}

	private class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == photoButton) {
				// open a file chooser
				JFileChooser chooser = new JFileChooser();
				int returnValue = chooser.showOpenDialog(null);

				// get the file name of the photo
				photoFileName = null;
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					photoFileName = selectedFile.getName();
				}

				// display file name of selected photo
				selectedPhotoLabel.setText(photoFileName);

			} else if (e.getSource() == createButton) {
				// check if every field is legal
				if (!isInteger(sellerIdTextField.getText())) {
					JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this,
							"Please enter an integer value for Seller ID.", "message", JOptionPane.WARNING_MESSAGE);

				} else if (!(rentRadioButton.isSelected() || saleRadioButton.isSelected())) {
					JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this, "Please select a purpose.",
							"message", JOptionPane.WARNING_MESSAGE);

				} else if (typeComboBox.getSelectedItem() == "") {
					JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this, "Please select a property type.",
							"message", JOptionPane.WARNING_MESSAGE);

				} else if (!isInteger(priceTextField.getText())) {
					JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this,
							"Please enter an integer for property price.", "message", JOptionPane.WARNING_MESSAGE);

				} else if (Double.parseDouble(priceTextField.getText()) <= 0) {
					JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this, "Please enter a positive number.",
							"message", JOptionPane.WARNING_MESSAGE);

				} else if (selectedPhotoLabel.getText() == "") {
					JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this, "Please select a photo.",
							"message", JOptionPane.WARNING_MESSAGE);

				} else if (addressTextArea.getText().trim().length() <= 0) {
					JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this,
							"Please enter the address of the property.", "message", JOptionPane.WARNING_MESSAGE);

				} else if (descriptionTextArea.getText().trim().length() <= 0) {
					JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this,
							"Please enter the description of the property.", "message", JOptionPane.WARNING_MESSAGE);

				} else {
					// sellerId
					int sellerId = Integer.parseInt(sellerIdTextField.getText());

					// purpose
					String purpose = null;
					if (rentRadioButton.isSelected()) {
						purpose = "For Rent";

					} else {
						purpose = "For Sale";
					}

					// type
					String type = (String) typeComboBox.getSelectedItem();

					// price
					int price = (int) Double.parseDouble(priceTextField.getText());

					// photo
					String photo = selectedPhotoLabel.getText();

					// address
					String address = addressTextArea.getText();

					// description
					String description = descriptionTextArea.getText();

					// initialize controller class object
					AgentCreatePropertyListingController controller = new AgentCreatePropertyListingController(userId);
					boolean ifSuccess = controller.createPropertyListing(sellerId, purpose, type, price, photo, address,
							description);

					if (ifSuccess) {
						JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this,
								"Property Listing successfully created.", "message", JOptionPane.INFORMATION_MESSAGE);

						// dispose this window
						AgentCreatePropertyListingUI.this.dispose();

					} else {
						JOptionPane.showMessageDialog(AgentCreatePropertyListingUI.this,
								"Property Listing failed to create.", "message", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
	}

	// determines if a string is an integer
	private static boolean isInteger(String x) {
		if (x == null) {
			return false;
		}

		try {
			Integer.parseInt(x);

		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	// determines if a string is a numeric
	private static boolean isNumeric(String x) {
		if (x == null) {
			return false;

		}

		try {
			Double.parseDouble(x);

		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
}
