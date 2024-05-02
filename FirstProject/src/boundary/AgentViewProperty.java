package boundary;

import control.SuspendUserController;
import control.ViewAllPropertiesController;
import entity.PropertyListing;
import entity.UserAccount;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AgentViewProperty extends JFrame {
    private JTable propertyTable;
    private ViewAllPropertiesController viewPropertiesController;
    
    private JButton backButton, createPropertyButton, logoutButton, refreshButton;

    public AgentViewProperty() {
        super("Agent Dashboard");
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        JPanel controlPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Back");
        leftPanel.add(backButton);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshButton = new JButton("Refresh");
        createPropertyButton = new JButton("Create Property");
        logoutButton = new JButton("Logout");
        rightPanel.add(refreshButton);
        rightPanel.add(createPropertyButton);
        rightPanel.add(logoutButton);
        
        controlPanel.add(leftPanel, BorderLayout.WEST);
        controlPanel.add(rightPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.NORTH);
        
        propertyTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(propertyTable);
        add(scrollPane, BorderLayout.CENTER);
        
        setupButtons();
        displayProperties();
    }

    
    private void setupButtons() {
        backButton.addActionListener(e -> {
            // Code to go back, perhaps to a main menu or previous page
            System.out.println("Back button pressed");
        });
        
        createPropertyButton.addActionListener(e -> {
            // Code to open the create property dialog
            System.out.println("Create property button pressed");
        });
        
        refreshButton.addActionListener(e -> {
            displayProperties();
        });
        
        logoutButton.addActionListener(e -> {
            dispose();
            LoginUI loginWindow = new LoginUI();
            loginWindow.setVisible(true);
        });
    }
    
    private void displayProperties() {
    	PropertyListing propertyListing = new PropertyListing();  
        viewPropertiesController = new ViewAllPropertiesController(propertyListing);
        
        List<String[]> properties = viewPropertiesController.getAllUsers();
        updateTableWithPropertyData(properties);
        
        propertyTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // Assuming the first column contains the property ID
                    String propertyID = table.getModel().getValueAt(row, 0).toString(); 
//                    AgentUpdateUI agentUpdateDialog = new AgentUpdateUI(AgentViewProperty.this, propertyID);
//                    agentUpdateDialog.setVisible(true);
                }
            }
        });
    }

    
    private void updateTableWithPropertyData(List<String[]> properties) {
        String[] columnNames = {"Type", "Location", "Description", "Pricing", "Status"};
        String[][] data = new String[properties.size()][5];
        for (int i = 0; i < properties.size(); i++) {
            data[i] = properties.get(i);
        }
        propertyTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        propertyTable.revalidate();
        propertyTable.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgentViewProperty());
    }
}

