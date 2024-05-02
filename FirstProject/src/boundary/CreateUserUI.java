package boundary;
	
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.util.List;


import javax.swing.filechooser.FileNameExtensionFilter;

import control.CreateUserController;
import control.GetTypeOfProfileController;
import entity.UserAccount;
import entity.UserProfile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
	
public class CreateUserUI extends JDialog {
    private JTextField usernameField, emailField, nameField, phoneField;
    private JPasswordField passwordField, confirmPasswordField;
    private JTextArea descriptionArea;
    private JButton createButton;
    private JComboBox<String> userTypeCombo;
    private JLabel imageLabel;
    private File selectedImageFile; 
    public String filename;

    public CreateUserUI(Frame owner, boolean modal) {
        super(owner, modal);
        setTitle("Create New User");
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Image Upload Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBorder(BorderFactory.createTitledBorder("Profile Image"));

        JButton uploadButton = new JButton("Upload Image");
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
        usernameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        // Call to the controller to get user types from the database
        UserProfile userProfile = new UserProfile();
	    GetTypeOfProfileController getTypeOfProfileController = new GetTypeOfProfileController(userProfile);
	    List<String> userTypes = getTypeOfProfileController.getUserTypes( );
        
        for (String userType : userTypes) {
            userTypeCombo.addItem(userType);
        }
        
        addComponent(accountPanel, new JLabel("User Type:"), 0, 0);
        addComponent(accountPanel, userTypeCombo, 1, 0);
        addComponent(accountPanel, new JLabel("Username:"), 0, 1);
        addComponent(accountPanel, usernameField, 1, 1);
        addComponent(accountPanel, new JLabel("Email:"), 0, 2);
        addComponent(accountPanel, emailField, 1, 2);
        addComponent(accountPanel, new JLabel("Password:"), 0, 3);
        addComponent(accountPanel, passwordField, 1, 3);
        addComponent(accountPanel, new JLabel("Confirm Password:"), 0, 4);
        addComponent(accountPanel, confirmPasswordField, 1, 4);

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
        addComponent(personalPanel, new JLabel("Phone Number:"), 0, 2);
        addComponent(personalPanel, phoneField, 1, 2);

        constraints.gridy = 2;
        getContentPane().add(personalPanel, constraints);

        createButton = new JButton("Create User");
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        getContentPane().add(createButton, constraints);
        
        setupCreateUserButton();

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

    private void uploadImageAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg"));
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();  // Store the selected file
            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(selectedImageFile));
                Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        filename = timestamp + "_" + randomUUID + ".png"; 

        File destination = new File(directory, filename);
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
            JOptionPane.showMessageDialog(this, "Error saving image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void setupCreateUserButton() {
    	createButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			if (selectedImageFile != null) {
    		        saveImage(selectedImageFile);  
    		    }
    		    System.out.println("Creating user...");
    		    
    		    String username = usernameField.getText() ;
    		    String email = emailField.getText();
    		    String name = nameField.getText();
    		    String phone = phoneField.getText();
    		    String description = descriptionArea.getText();
    		    String password = new String(passwordField.getPassword());
    		    String confirmPassword = new String(confirmPasswordField.getPassword());
    		    String userType = (String) userTypeCombo.getSelectedItem();
    		    if(!password.equals(confirmPassword)) {
    		    	JOptionPane.showMessageDialog(CreateUserUI.this, "The password does not match");
    		    	
    		    	
    		    } else {
    		    	
    		
        		    
    		    	UserAccount userAccount = new UserAccount(username, email, name, phone, description, password, userType, filename);
                    CreateUserController createUserController = new CreateUserController(userAccount);
                    String createUserStatus = createUserController.createNewUser(username, email, name, phone, description, password, userType, filename);
                    System.out.println(createUserStatus);
        		    
        		    
        		    if(createUserStatus.contains("already exists")) {
        		    	JOptionPane.showMessageDialog(CreateUserUI.this, createUserStatus, "Error",JOptionPane.ERROR_MESSAGE);
        		    } else {
        		    	JOptionPane.showMessageDialog(CreateUserUI.this, createUserStatus);
            		    dispose();
        		    }
        		    
    		    	
    		    }
    		    
    		   
    		}
    	});
    	
    	
    	
    }

    public static void main(String[] args) {
        CreateUserUI dialog = new CreateUserUI(new JFrame(), true);
        dialog.setVisible(true);
    }
}
