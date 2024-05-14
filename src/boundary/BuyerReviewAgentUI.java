package boundary;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import control.BuyerReviewAgentController;

public class BuyerReviewAgentUI extends JDialog {
	private BuyerSelectAgentUI parent;
	private int agentId;

	private JTextArea reviewTextArea;

	private JButton submitButton;

	public BuyerReviewAgentUI(BuyerSelectAgentUI parent, int agentId, int x, int y) {
		// set basic setting
		super(parent, "Review the selected agent");
		this.parent = parent;
		this.agentId = agentId;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(x + 580, y, 300, 220);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER));

		// create text area
		reviewTextArea = new JTextArea(8, 25);
		reviewTextArea.setMargin(new Insets(5, 0, 5, 0));
		// add text area to the panel
		add(reviewTextArea);

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

			if (reviewTextArea.getText().trim().length() <= 0) {
				JOptionPane.showMessageDialog(BuyerReviewAgentUI.this,
						"Please put comment in the text area to review the agent.", "message",
						JOptionPane.WARNING_MESSAGE);

			} else {
				String review = reviewTextArea.getText();

				// initialize controller for rating agent
				BuyerReviewAgentController controller = new BuyerReviewAgentController(parent.getUserId());

				// check if the rating is submitted successfully
				if (controller.reviewAgent(agentId, review)) {
					JOptionPane.showMessageDialog(BuyerReviewAgentUI.this, "Review submitted successfully", "message",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(BuyerReviewAgentUI.this, "Review failed to submit", "message",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}

			// dispose this page
			BuyerReviewAgentUI.this.dispose();
		}
	}
}