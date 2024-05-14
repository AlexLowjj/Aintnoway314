package boundary;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import control.BuyerRateAgentController;

public class BuyerRateAgentUI extends JDialog {
	private BuyerSelectAgentUI parent;
	private int agentId;

	private JRadioButton one;
	private JRadioButton two;
	private JRadioButton three;
	private JRadioButton four;
	private JRadioButton five;
	private ButtonGroup group;

	private JButton submitButton;

	public BuyerRateAgentUI(BuyerSelectAgentUI parent, int agentId, int x, int y) {
		// set basic setting
		super(parent, "Rate the selected agent");
		this.parent = parent;
		this.agentId = agentId;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(x + 580, y + 50, 300, 80);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.LEFT));

		// create radio buttons
		one = new JRadioButton("1");
		one.setActionCommand("1");
		two = new JRadioButton("2");
		one.setActionCommand("2");
		three = new JRadioButton("3");
		one.setActionCommand("3");
		four = new JRadioButton("4");
		one.setActionCommand("4");
		five = new JRadioButton("5");
		one.setActionCommand("5");

		// add buttons to panel
		add(one);
		add(two);
		add(three);
		add(four);
		add(five);

		// add buttons to group
		group = new ButtonGroup();
		group.add(one);
		group.add(two);
		group.add(three);
		group.add(four);
		group.add(five);

		// create submit button
		submitButton = new JButton("submit");

		// add event listener
		SubmitButtonListener submitListener = new SubmitButtonListener();
		submitButton.addActionListener(submitListener);

		// add button to panel
		add(submitButton);

		setVisible(true);
	}

	private class SubmitButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (group.getSelection() == null) {
				JOptionPane.showMessageDialog(BuyerRateAgentUI.this,
						"Please select a rating for the agent before submit.", "message", JOptionPane.WARNING_MESSAGE);

			} else {
				int rating = -1;
				if (one.isSelected()) {
					rating = 1;
				} else if (two.isSelected()) {
					rating = 2;
				} else if (three.isSelected()) {
					rating = 3;
				} else if (four.isSelected()) {
					rating = 4;
				} else if (five.isSelected()) {
					rating = 5;
				}

				// initialize controller for rating agent
				BuyerRateAgentController controller = new BuyerRateAgentController(parent.getUserId());

				// check if the rating is submitted successfully
				if (controller.rateAgent(agentId, rating)) {
					JOptionPane.showMessageDialog(BuyerRateAgentUI.this, "Rating submitted successfully", "message",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(BuyerRateAgentUI.this, "Rating failed to submit", "message",
							JOptionPane.INFORMATION_MESSAGE);
				}

				// dispose this page
				BuyerRateAgentUI.this.dispose();
			}
		}
	}
}