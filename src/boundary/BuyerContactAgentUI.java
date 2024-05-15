package boundary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import control.BuyerContactAgentController;
import entity.Agent;

public class BuyerContactAgentUI extends JFrame {
	private final int userId;
	private final BuyerContactAgentController contactAgentController;
	private final JTable agentTable;

	public BuyerContactAgentUI(int userId) {
		super("Contact Real Estate Agent");
		this.userId = userId;
		this.contactAgentController = new BuyerContactAgentController(userId);
		this.agentTable = new JTable();
		agentTable.setRowSelectionAllowed(true);
		;
		initializeComponents();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initializeComponents() {
		setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(agentTable);
		add(scrollPane, BorderLayout.CENTER);

		searchAgents();
	}

	private void searchAgents() {
		List<Agent> agents = contactAgentController.getAgentContacts();
		displayAgents(agents);
	}

	private void displayAgents(List<Agent> agents) {
		String[] columnNames = { "Username", "Phone" };
		Object[][] data = new Object[agents.size()][columnNames.length];

		for (int i = 0; i < agents.size(); i++) {
			Agent agent = agents.get(i);
			data[i][0] = agent.getUsername();
			data[i][1] = agent.getPhone();
		}

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		agentTable.setModel(model);
	}
}