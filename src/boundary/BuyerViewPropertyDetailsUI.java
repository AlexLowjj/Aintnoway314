package boundary;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import control.BuyerAddFavouritePropertyController;
import control.BuyerGetPropertyByCharController;
import entity.BuyerPropertyListing;

public class BuyerViewPropertyDetailsUI extends JDialog {
    private JLabel imageLabel, typeLabel, locationLabel, descriptionLabel, pricingLabel, statusLabel, agentNameLabel, phoneLabel;
    private JButton closeButton, favouriteButton;
    private String propertyID, tempHoldPropertyID;

    public BuyerViewPropertyDetailsUI(Frame owner, boolean modal, String propertyID) {
        super(owner, modal);
        this.propertyID = propertyID;
        System.out.println("hello"+propertyID);
        setTitle("View Property Details");
        getContentPane().setLayout(new GridBagLayout());
        initializeComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(400, 400));  // Ensure minimum size is respected
        setLocationRelativeTo(owner);
        loadData();
    }

    private void initializeComponents() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        initializePhotoPanel(constraints);
        initializeDetailsPanel(constraints);
        initializeButtons(constraints);
    }

    private void initializePhotoPanel(GridBagConstraints constraints) {
        JPanel photoPanel = new JPanel();
        photoPanel.setBorder(BorderFactory.createTitledBorder("Property Photo"));
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        photoPanel.add(imageLabel);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(photoPanel, constraints);
    }

    private void initializeDetailsPanel(GridBagConstraints constraints) {
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Property Details"));

        JLabel[] labels = {new JLabel("Type:"), new JLabel("Location:"), new JLabel("Description:"), new JLabel("Pricing:"), new JLabel("Status:"), new JLabel("Agent Name:"), new JLabel("Phone:")};
        JLabel[] valueLabels = {typeLabel = new JLabel(), locationLabel = new JLabel(), descriptionLabel = new JLabel(), pricingLabel = new JLabel(), statusLabel = new JLabel(), agentNameLabel = new JLabel(), phoneLabel = new JLabel()};

        for (int i = 0; i < labels.length; i++) {
            addComponent(detailsPanel, labels[i], valueLabels[i], 0, i);
        }

        constraints.gridy = 1;
        getContentPane().add(detailsPanel, constraints);
    }

    private void addComponent(JPanel panel, JLabel label, JLabel field, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void initializeButtons(GridBagConstraints constraints) {
        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        favouriteButton = new JButton("Favourite");
        favouriteButton.addActionListener(e -> {
        	System.out.println("checking 1");
        	String[] parts = propertyID.split("-");
        	String userid = parts[4];
        	
        	System.out.println(userid);
        	System.out.println(tempHoldPropertyID);
        	
        	BuyerPropertyListing buyerPropertyListing = new BuyerPropertyListing(userid, tempHoldPropertyID);
        	BuyerAddFavouritePropertyController buyerAddFavouriteProperty = new BuyerAddFavouritePropertyController(buyerPropertyListing);
		    Boolean status = buyerAddFavouriteProperty.addFavouriteProperty(userid, tempHoldPropertyID);
		    if(status) {
		    	JOptionPane.showMessageDialog(BuyerViewPropertyDetailsUI.this, "Property Added successfully");
		    } else {
		    	JOptionPane.showMessageDialog(BuyerViewPropertyDetailsUI.this, "Favourite was failed");
		    }
		    
		    
		    
        });
       
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(favouriteButton);
        buttonPanel.add(closeButton);

        constraints.gridy = 2;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        getContentPane().add(buttonPanel, constraints);
    }

    private void loadData() {
        String[] parts = propertyID.split("-");

        BuyerGetPropertyByCharController controller = new BuyerGetPropertyByCharController(new BuyerPropertyListing());
        java.util.List<String> propertyDetails = controller.getPropertyByChar(parts[0], parts[1], parts[3], parts[2]);

        if (!propertyDetails.isEmpty()) {
        	tempHoldPropertyID = propertyDetails.get(0);
            displayImage(propertyDetails.get(1));  
            typeLabel.setText(propertyDetails.get(4));
            locationLabel.setText(propertyDetails.get(2));
            descriptionLabel.setText(propertyDetails.get(3));
            pricingLabel.setText(propertyDetails.get(5));
            statusLabel.setText(propertyDetails.get(6));
            agentNameLabel.setText(propertyDetails.get(7));
            phoneLabel.setText(propertyDetails.get(8));
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
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
