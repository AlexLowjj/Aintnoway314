package boundary;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import control.SellerGetAgentController;
import entity.Agent;

public class SellerSelectAgentUI extends JFrame {

	private String type;
	private int userId;
	private int x;
	private int y;

	private JPanel mainPanel;
	private JScrollPane scrollPanel;

	public SellerSelectAgentUI(String type, int userId, int x, int y) {
		// set title
		super("Select one of your agents");
		// set type
		this.type = type;
		// set user id
		this.userId = userId;
		// set location parameter
		this.x = x;
		this.y = y;
		// set basic setting of the main frame
		setResizable(true);
		setBounds(x + 250, y, 330, 220);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		// set components
		setComponents();
	}

	public int getUserId() {
		return userId;
	}

	private void setComponents() {
		SellerGetAgentController controller = new SellerGetAgentController(userId);
		ArrayList<Agent> agents = controller.getMyAgents();

		if (agents != null) {
			mainPanel = new JPanel(new GridLayout(agents.size(), 1));

			// initialize event listener
			AgentButtonListener listener = new AgentButtonListener();

			// for each agent
			for (int i = 0; i < agents.size(); i++) {
				// create button for each agent
				HyperLinkButtonUI agentButton = new HyperLinkButtonUI(
						String.format("> %s, agent id: %d", agents.get(i).getUsername(), agents.get(i).getUserId()),
						new Color(16, 9, 192));
				agentButton.setActionCommand(String.valueOf(agents.get(i).getUserId()));
				// add event listener to the button
				agentButton.addActionListener(listener);

				// add button to the outer panel
				JPanel outter = new JPanel(new FlowLayout(FlowLayout.LEFT));
				outter.add(agentButton);

				// add outter panel to the main panel
				mainPanel.add(outter);
			}

			// add main panel to the scroll panel
			scrollPanel = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			// add scroll panel to the main frame
			add(scrollPanel);

		} else {
			// if failed to find agent, dispose this page after click ok
			JOptionPane.showMessageDialog(SellerSelectAgentUI.this, "Failed to find your agent.", "message",
					JOptionPane.WARNING_MESSAGE);
			SellerSelectAgentUI.this.dispose();
		}
	}

	// define ActionListener
	private class AgentButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// get user name of the selected agent
			int agentId = Integer.parseInt(e.getActionCommand());

			if (type == "rate") {
				// create dialog for rate agent
				SellerRateAgentUI dialog = new SellerRateAgentUI(SellerSelectAgentUI.this, agentId, x, y);

			} else if (type == "review") {
				// create dialog for review agent
				SellerReviewAgentUI dialog = new SellerReviewAgentUI(SellerSelectAgentUI.this, agentId, x, y);

			}
		}
	}

}