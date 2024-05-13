package boundary;

import control.SearchUserController;
import control.SuspendProfileController;
import control.SuspendUserController;
import control.ViewAllUserController;
import entity.UserAccount;
import entity.UserProfile;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Point;

import java.awt.*;
import java.util.List;

public class AdminUI extends JFrame {
    private JTable userTable;
    private ViewAllUserController viewUserController;
    private SearchUserController searchUserController;
    
    private JTextField searchField;
    private JButton searchButton;
   
    private JButton createUserButton,logoutButton,showUserRoleButton,refreshButton;;

    public AdminUI() {
        super("Admin Dashboard");
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
        inputPanel.add(new JLabel("Enter Email:"));
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        searchPanel.add(inputPanel, BorderLayout.CENTER);

        // function right panel (user role, create user, logout)
        JPanel createUserPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Logout");
        createUserButton = new JButton("Create User");
        showUserRoleButton = new JButton("User Profile");
        createUserButton.addActionListener(e -> {
            CreateUserUI createUserDialog = new CreateUserUI(this, true);
            createUserDialog.setVisible(true);
        });
        refreshButton = new JButton("Refresh");
        createUserPanel.add(refreshButton);
        createUserPanel.add(showUserRoleButton);
        createUserPanel.add(createUserButton);
        createUserPanel.add(logoutButton);
        searchPanel.add(createUserPanel, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);
        
        // Table to display users
        userTable = new JTable();
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);
        displayUsers();
        
        setupSearchButton();
        setUpLogoutButton();
        setupUserRoleButton();
        setupRefreshButton();
    }
    
    private void setupUserRoleButton() {
    	showUserRoleButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			dispose(); 
    		    UserProfileUI userProfileUI = new UserProfileUI(); 
    		    userProfileUI.setVisible(true);
;    			
    		}
    	});
    };

    private void setupRefreshButton() {
    	refreshButton.addActionListener(e -> {
    		UserAccount userAccount = new UserAccount();  
            viewUserController = new ViewAllUserController(userAccount);
            
            List<String[]> users = viewUserController.getAllUsers();
            updateTableWithSearchResults(users);
        });
    }
    
    private void setUpLogoutButton() {
    	logoutButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			dispose(); 
    		    LoginUI loginWindow = new LoginUI(); 
    		    loginWindow.setVisible(true);
;    			
    		}
    	});
    };
    
    private void setupSearchButton() {
    	searchButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
                
    			String searchInput = searchField.getText();
    			
    			UserAccount userAccount = new UserAccount(searchInput);  
    			searchUserController = new SearchUserController(userAccount);
    		        
    		    List<String[]> users = searchUserController.getUserByEmail(searchInput);
    		    updateTableWithSearchResults(users);
    		        
    			
    		}
    	});
    };
    
    private void displayUsers() {
        UserAccount userAccount = new UserAccount();  
        viewUserController = new ViewAllUserController(userAccount);
        
        List<String[]> users = viewUserController.getAllUsers();
        updateTableWithSearchResults(users);
        
        //double click to get user info
        userTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && row != -1) {
                    String passingUserID = table.getModel().getValueAt(row, 0).toString(); 
                    
                    String[] options = {"Update User", "Suspend User"};
                    int choice = JOptionPane.showOptionDialog(null, "What would you like to do?", "User Management", 
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    
                    switch (choice) {
                    case 0:
                    	UpdateUserUI updateUserDialog = new UpdateUserUI(AdminUI.this, true, passingUserID);
                        updateUserDialog.setVisible(true);
                        break;
                    case 1: 
                    	UserAccount userAccount = new UserAccount();  
                    	SuspendUserController suspendUserController = new SuspendUserController(userAccount);
                        
                        boolean suspendStatus = suspendUserController.suspendUser(passingUserID);
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
    	
    private void updateTableWithSearchResults(List<String[]> usersByEmail) {
        String[] columnNames = {"User ID","Username", "Email", "Profile","is_suspended"};
        String[][] data = new String[usersByEmail.size()][5];
        for (int i = 0; i < usersByEmail.size(); i++) {
            data[i] = usersByEmail.get(i);
        }
        userTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        userTable.revalidate();
        userTable.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminUI());
    }
}
