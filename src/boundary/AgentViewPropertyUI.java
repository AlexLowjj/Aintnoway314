package boundary;

import control.SuspendPropertyController;
import control.ViewAllPropertiesController;
import entity.PropertyListing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AgentViewPropertyUI extends JFrame {
	private int userId;

	private JTable propertyTable;
	private ViewAllPropertiesController viewPropertiesController;

	private JButton backButton, createPropertyButton, logoutButton, refreshButton;

	public AgentViewPropertyUI(int userId) {
		super("Agent Dashboard");
		this.userId = userId;
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
			dispose();
			AgentUI agentWindow = new AgentUI(userId);
			agentWindow.setVisible(true);
		});

		createPropertyButton.addActionListener(e -> {
			CreatePropertyUI loginWindow = new CreatePropertyUI(new JFrame(), true);
			loginWindow.setVisible(true);
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

		// double click to get info
		propertyTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table = (JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int row = table.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
					String type = table.getModel().getValueAt(row, 0).toString();
					String location = table.getModel().getValueAt(row, 1).toString();
					String price = table.getModel().getValueAt(row, 3).toString();

					String[] options = { "Update Properties", "Suspend Properties" };
					int choice = JOptionPane.showOptionDialog(null, "What would you like to do?", "User Management",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

					switch (choice) {
					case 0:
						String infoHold = type + "-" + location + "-" + price;
						UpdatePropertyUI updatePropertyUI = new UpdatePropertyUI(AgentViewPropertyUI.this, true,
								infoHold);
						updatePropertyUI.setVisible(true);
						break;
					case 1:
						PropertyListing propertyListing = new PropertyListing();
						SuspendPropertyController suspendPropertyController = new SuspendPropertyController(
								propertyListing);

						boolean suspendStatus = suspendPropertyController.suspendProperties(type, location, price);
						if (suspendStatus) {
							JOptionPane.showMessageDialog(null, "Properties had suspended succesfully");
							break;
						} else {
							JOptionPane.showMessageDialog(null, "Fail to suspend the properties");
							break;
						}

					}
				}
			}
		});
	}

	private void updateTableWithPropertyData(List<String[]> properties) {
		String[] columnNames = { "Type", "Location", "Description", "Pricing", "Status" };
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
		SwingUtilities.invokeLater(() -> new AgentViewPropertyUI(2));
	}

}
