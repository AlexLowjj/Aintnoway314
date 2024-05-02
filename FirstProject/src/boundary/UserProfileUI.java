package boundary;
	
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
	
import control.CreateProfileController;
import control.SearchProfileController;
import control.SuspendProfileController;
import control.ViewAllProfilesController;

import entity.UserProfile;
	
public class UserProfileUI extends JFrame {
    private JTable roleTable;

    private JTextField searchField;
    private JButton searchButton;

    private JButton createRoleButton, logoutButton, showUserButton,refreshButton;

    public UserProfileUI() {
        super("Role Management Dashboard");
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());

        // function left panel (search)
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        inputPanel.add(new JLabel("Enter Profile Name:"));
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        searchPanel.add(inputPanel, BorderLayout.CENTER);

        // function right panel (create role, show users, logout)
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Logout");
        createRoleButton = new JButton("Create Profile");
        showUserButton = new JButton("Show Users");
        refreshButton = new JButton("Refresh");
        rolePanel.add(refreshButton);
        rolePanel.add(createRoleButton);
        rolePanel.add(showUserButton);
        rolePanel.add(logoutButton);
        searchPanel.add(rolePanel, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        // Table to display roles
        roleTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(roleTable);
        add(scrollPane, BorderLayout.CENTER);
        displayRoles();

        setupSearchButton();
        setUpLogoutButton();
        setupCreateRoleButton();
        setupRefreshButton();
    }

    private void setUpLogoutButton() {
        logoutButton.addActionListener(e -> {
            dispose();
            LoginUI loginWindow = new LoginUI();
            loginWindow.setVisible(true);
        });
    }
    
    private void createRoleDialog() {
        JFrame frame = new JFrame("Create New Role");
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(this); 

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        JLabel nameLabel = new JLabel("Role Name:");
        JTextField nameField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");

        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            
            UserProfile userProfile = new UserProfile(name, description);  
	    	CreateProfileController createProfileController = new CreateProfileController(userProfile);
	        
	        boolean updateStatus = createProfileController.createNewProfile(name, description);
	        if(updateStatus) {
	        	JOptionPane.showMessageDialog(frame, "Profile had created successfully", "Creation Status", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); 
                displayRoles();
	        } else {
	        	JOptionPane.showMessageDialog(frame, "Failed to create new profile: " , "Error", JOptionPane.ERROR_MESSAGE);
	        }
            
           
        });

        cancelButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }



    private void setupSearchButton() {
        searchButton.addActionListener(e -> {
            String searchInput = searchField.getText();
            
            UserProfile userProfile = new UserProfile(searchInput);  
	    	SearchProfileController searchProfileController = new SearchProfileController(userProfile);
	        
	        List<String[]> rolesByName = searchProfileController.getProfileByName(searchInput);
	        updateTableWithSearchResults(rolesByName);	
        });
    }
    private void setupRefreshButton() {
    	refreshButton.addActionListener(e -> {
    		UserProfile userProfile = new UserProfile();  
	    	ViewAllProfilesController viewAllProfilesController = new ViewAllProfilesController(userProfile);
	        
	        List<String[]> profiles = viewAllProfilesController.getAllProfiles();
	        updateTableWithSearchResults(profiles);	
        });
    }
    
    private void setupCreateRoleButton() {
    	createRoleButton.addActionListener(e -> {
    		createRoleDialog();
        });
    }

    private void displayRoles() {
    	UserProfile userProfile = new UserProfile();  
    	ViewAllProfilesController viewAllProfilesController = new ViewAllProfilesController(userProfile);
        
        List<String[]> profiles = viewAllProfilesController.getAllProfiles();
        updateTableWithSearchResults(profiles);	
        // double click to get role info
        roleTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && row != -1) {
                	String passingID = table.getModel().getValueAt(row, 0).toString(); 
                	
                	String[] options = {"Update Profile", "Suspend Profile"};
                    int choice = JOptionPane.showOptionDialog(null, "What would you like to do?", "User Management", 
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    
                    switch (choice) {
                    case 0:
                    	UpdateProfileUI updateUserUI = new UpdateProfileUI(UserProfileUI.this, true, passingID);
                    	updateUserUI.setVisible(true);
                        break;
                    case 1: 
                    	UserProfile userProfile = new UserProfile();  
                    	SuspendProfileController suspendProfileController = new SuspendProfileController(userProfile);
                        
                        boolean suspendStatus = suspendProfileController.suspendProfile(passingID);
                        if(suspendStatus) {
                        	JOptionPane.showMessageDialog(null, "Profile had suspended succesfully");
                            break;
                        } else {
                        	JOptionPane.showMessageDialog(null, "Fail to suspend the profile");
                            break;
                        }
                        
                    
                    }
                	

                }
            }
        });
    }

    private void updateTableWithSearchResults(List<String[]> roles) {
        String[] columnNames = {"Profile ID", "Profile Name", "Description", "Status"};
        String[][] data = new String[roles.size()][4];
        for (int i = 0; i < roles.size(); i++) {
            data[i] = roles.get(i);
        }
        roleTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        roleTable.revalidate();
        roleTable.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserProfileUI());
    }
}
