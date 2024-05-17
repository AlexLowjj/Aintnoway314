package boundary;

import control.BuyerCalculateMortageController;
import control.BuyerGetFavouritePropertyController;
import control.BuyerGetForSalePropertyController;
import control.BuyerGetLocationController;
import control.BuyerGetPropertyTypeController;
import control.BuyerGetSoldPropertyController;
import control.BuyerViewAllPropertiesController;
import control.BuyerViewPropertyByCharController;
import entity.BuyerPropertyListing;
import entity.CalculateMortage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BuyerViewAllPropertiesUI extends JFrame {
    private int userId;

    private JComboBox<String> cbLocation = new JComboBox<>();
    private JComboBox<String> cbType = new JComboBox<>(new String[]{"", "House", "Apartment"});
    private JButton btnSearch = new JButton("Search");
    private JButton btnFilter = new JButton("Filter");
    private JButton btnCalculateMortgage = new JButton("Calculate Mortgage");

    private JTable propertyTable;
    private BuyerViewAllPropertiesController buyerViewPropertiesController;
    BuyerPropertyListing buyerPropertyListing = new BuyerPropertyListing();

    private JButton backButton, logoutButton;

    public BuyerViewAllPropertiesUI(int userId) {
        super("Buyer Dashboard");
        this.userId = userId;
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        
        String location = (cbLocation.getSelectedItem() != null) ? cbLocation.getSelectedItem().toString() : "";
        String type = (cbType.getSelectedItem() != null) ? cbType.getSelectedItem().toString() : "";
        
        
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new BorderLayout());

        // Left panel
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("Back");
        leftPanel.add(backButton);

        // Middle panel
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanel.add(new JLabel("Location:"));
        middlePanel.add(cbLocation);
        middlePanel.add(new JLabel("Type:"));
        middlePanel.add(cbType);
        middlePanel.add(btnSearch);

        // Right panel
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(btnFilter);
        rightPanel.add(btnCalculateMortgage);
        logoutButton = new JButton("Logout");
        rightPanel.add(logoutButton);

        controlPanel.add(leftPanel, BorderLayout.WEST);
        controlPanel.add(middlePanel, BorderLayout.CENTER);
        controlPanel.add(rightPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.NORTH);

        propertyTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(propertyTable);
        add(scrollPane, BorderLayout.CENTER);

        setupButtons();
        displayProperties();
        
        BuyerGetLocationController buyerGetUserTypesController = new BuyerGetLocationController(buyerPropertyListing);
        List<String> locationTypes = buyerGetUserTypesController.getLocationType();
        cbLocation.addItem("");
        for (String locationType : locationTypes) {
            cbLocation.addItem(locationType);
        }
        
        BuyerGetPropertyTypeController buyerGetPropertyTypeController = new BuyerGetPropertyTypeController(buyerPropertyListing);
        List<String> propertyTypes = buyerGetPropertyTypeController.getPropertyType();
        cbType.addItem("");
        for (String propertyType : propertyTypes) {
        	cbType.addItem(propertyType);
        }
    }

    private void setupButtons() {
        btnSearch.addActionListener(e -> {
        	String location = cbLocation.getSelectedItem().toString();
        	String type = cbType.getSelectedItem().toString();
        	
        	BuyerViewPropertyByCharController buyerViewPropertyByCharController = new BuyerViewPropertyByCharController(buyerPropertyListing);

            List<String[]> properties = buyerViewPropertyByCharController.getPropertyByLocationAndType(location,type);
            updateTableWithPropertyData(properties);
        	
        	
        	
        });

        btnFilter.addActionListener(e -> showFilterOptions());

        btnCalculateMortgage.addActionListener(e -> calculateMortgage());

        logoutButton.addActionListener(e -> {
            dispose();
            LoginUI loginWindow = new LoginUI();
            loginWindow.setVisible(true);
        });

        backButton.addActionListener(e -> {
            dispose();
            // Navigate to the previous UI, e.g., AgentUI
            // AgentUI agentWindow = new AgentUI(userId);
            // agentWindow.setVisible(true);
            BuyerMenuUI buyerMenuUI = new BuyerMenuUI(userId);
        });
    }

    private void displayProperties() {
        buyerViewPropertiesController = new BuyerViewAllPropertiesController(buyerPropertyListing);

        List<String[]> properties = buyerViewPropertiesController.getAllProperty();
        updateTableWithPropertyData(properties);
        
      //double click to get info
        propertyTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    String description = table.getModel().getValueAt(row, 0).toString(); 
                    String type = table.getModel().getValueAt(row, 1).toString(); 
                    String location = table.getModel().getValueAt(row, 2).toString(); 
                    String price = table.getModel().getValueAt(row, 3).toString(); 
                    
                    String infoHold = type + "-" + location + "-" + description +"-" + price+"-"+userId;
                    System.out.println(infoHold);
                    
                    BuyerViewPropertyDetailsUI buyerViewPropertyDetailsUI = new BuyerViewPropertyDetailsUI(BuyerViewAllPropertiesUI.this, true,infoHold);
                    buyerViewPropertyDetailsUI.setVisible(true);    
                    
                
                   
                }
            }
        });
    }



    private void updateTableWithPropertyData(List<String[]> properties) {
        String[] columnNames = {"Description", "Type", "Location", "Pricing", "Status", "Time of View"};
        String[][] data = new String[properties.size()][6];
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

    private void showFilterOptions() {
        JDialog filterDialog = new JDialog(this, "Filter Options", true);
        filterDialog.setLayout(new FlowLayout());

        // Create radio buttons for filtering options
        JRadioButton allButton = new JRadioButton("All");
        JRadioButton forSaleButton = new JRadioButton("For Sale");
        JRadioButton soldButton = new JRadioButton("Sold");
        JRadioButton favouriteButton = new JRadioButton("Favourite"); // New radio button for favourite properties

        // Group the radio buttons
        ButtonGroup filterGroup = new ButtonGroup();
        filterGroup.add(allButton);
        filterGroup.add(forSaleButton);
        filterGroup.add(soldButton);
        filterGroup.add(favouriteButton); // Add the new button to the group

        allButton.setSelected(true); // Default selected button

        // Add radio buttons to the dialog
        filterDialog.add(allButton);
        filterDialog.add(forSaleButton);
        filterDialog.add(soldButton);
        filterDialog.add(favouriteButton); // Add the new button to the dialog

        // Button to apply the selected filter
        JButton applyFilterButton = new JButton("Apply Filter");
        applyFilterButton.addActionListener(e -> {
            if (allButton.isSelected()) {
                displayProperties();
            } else if (forSaleButton.isSelected()) {
                BuyerGetForSalePropertyController buyerGetForSalePropertyController = new BuyerGetForSalePropertyController(buyerPropertyListing);
                List<String[]> properties = buyerGetForSalePropertyController.getForSaleProperty();
                updateTableWithPropertyData(properties);
            } else if (soldButton.isSelected()) {
                BuyerGetSoldPropertyController buyerGetSoldPropertyController = new BuyerGetSoldPropertyController(buyerPropertyListing);
                List<String[]> properties = buyerGetSoldPropertyController.getSoldProperty();
                updateTableWithPropertyData(properties);
            } else if (favouriteButton.isSelected()) {
            	BuyerGetFavouritePropertyController buyerGetFavouritePropertyController = new BuyerGetFavouritePropertyController(buyerPropertyListing);
            	String userIdStr = Integer.toString(userId);
                List<String[]> properties = buyerGetFavouritePropertyController.getFavouriteProperty(userIdStr);
                updateTableWithPropertyData(properties);
            }
            filterDialog.dispose();
        });

        filterDialog.add(applyFilterButton);
        filterDialog.setSize(300, 200);
        filterDialog.setLocationRelativeTo(this);
        filterDialog.setVisible(true);
    }


    private void calculateMortgage() {
        // Create a dialog with the current frame as its parent
        JDialog dialog = new JDialog(this, "Mortgage Calculator", true);
        dialog.setLayout(new GridLayout(0, 1, 10, 10)); // Set layout

        // Input fields setup
        JTextField loanAmountField = new JTextField(10);
        JTextField interestRateField = new JTextField(10);
        JTextField loanTermField = new JTextField(10);
        JTextField monthlyPaymentField = new JTextField(10);
        monthlyPaymentField.setEditable(false); // Set monthly payment field as non-editable

        // Adding components to dialog
        dialog.add(new JLabel("Loan Amount:"));
        dialog.add(loanAmountField);
        dialog.add(new JLabel("Interest Rate (%):"));
        dialog.add(interestRateField);
        dialog.add(new JLabel("Loan Term (years):"));
        dialog.add(loanTermField);
        dialog.add(new JLabel("Monthly Payment:"));
        dialog.add(monthlyPaymentField);

        // Buttons for performing the calculation and closing the dialog
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> {
            try {
                double loanAmount = Double.parseDouble(loanAmountField.getText());
                double interestRate = Double.parseDouble(interestRateField.getText());
                int loanTerm = Integer.parseInt(loanTermField.getText());

                // Assuming CalculateMortage and BuyerCalculateMortageController are defined elsewhere in your code
                CalculateMortage calculateMortage = new CalculateMortage(loanAmount, interestRate, loanTerm);
                BuyerCalculateMortageController buyerCalculateMortageController = new BuyerCalculateMortageController(calculateMortage);
                double calculateResult = buyerCalculateMortageController.calculateMonthlyPayment(loanAmount, interestRate, loanTerm);

                // Update the monthly payment text field with the result
                monthlyPaymentField.setText(String.format("%.2f", calculateResult));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        // Adding buttons to the dialog
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(calculateButton);
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel);

        // Suggest a preferred size before packing
        dialog.setPreferredSize(new Dimension(400, 500));
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
