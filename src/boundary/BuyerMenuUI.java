package boundary;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuyerMenuUI extends JFrame {
	private int userId;

	private JPanel titlePanel;
	private JLabel titleLabel;

	private JPanel propertyPanel;
	private JButton propertyButton;

	private JPanel ratePanel;
	private JButton rateButton;

	private JPanel reviewPanel;
	private JButton reviewButton;

	private JPanel contactPanel;
	private JButton contactButton;

	private JPanel logoutPanel;
	private JButton logoutButton;

	public BuyerMenuUI(int userId) {
		super("Buyer Menu");
		this.userId = userId;
		setSize(250, 220);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(6, 1));

		// set components
		setComponents();

		setVisible(true);
	}

	private void setComponents() {
		// title
		titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		titleLabel = new JLabel("Buyer Menu");

		titlePanel.add(titleLabel);

		// property
		propertyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		propertyButton = new JButton("Property");
		propertyButton.setPreferredSize(new Dimension(200, 25));

		propertyPanel.add(propertyButton);

		// rate
		ratePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		rateButton = new JButton("Rate My Agents");
		rateButton.setPreferredSize(new Dimension(200, 25));

		ratePanel.add(rateButton);

		// review
		reviewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		reviewButton = new JButton("Review My Agents");
		reviewButton.setPreferredSize(new Dimension(200, 25));

		reviewPanel.add(reviewButton);

		// contact
		contactPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		contactButton = new JButton("Contact My Agents");
		contactButton.setPreferredSize(new Dimension(200, 25));

		contactPanel.add(contactButton);

		// logout
		logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		logoutButton = new JButton("Log out ->>     ");
		logoutButton.setBorder(BorderFactory.createEmptyBorder());
		logoutButton.setContentAreaFilled(false);
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		logoutPanel.add(logoutButton);

		// add event handler
		ButtonListener listener = new ButtonListener();
		propertyButton.addActionListener(listener);
		rateButton.addActionListener(listener);
		reviewButton.addActionListener(listener);
		contactButton.addActionListener(listener);
		logoutButton.addActionListener(listener);

		// add components to main panel
		add(titlePanel);
		add(propertyPanel);
		add(ratePanel);
		add(reviewPanel);
		add(contactPanel);
		add(logoutPanel);
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == propertyButton) {
				BuyerViewAllPropertiesUI buyerViewAllPropertiesUI = new BuyerViewAllPropertiesUI(userId);
				BuyerMenuUI.this.dispose();

			} else if (e.getSource() == rateButton) {
				BuyerSelectAgentUI selectUI = new BuyerSelectAgentUI("rate", userId, BuyerMenuUI.this.getX(),
						BuyerMenuUI.this.getY());

			} else if (e.getSource() == reviewButton) {
				BuyerSelectAgentUI selectUI = new BuyerSelectAgentUI("review", userId, BuyerMenuUI.this.getX(),
						BuyerMenuUI.this.getY());

			} else if (e.getSource() == contactButton) {
				BuyerContactAgentUI contactUI = new BuyerContactAgentUI(2);

			} else if (e.getSource() == logoutButton) {
				// dispose buyer menu page
				BuyerMenuUI.this.dispose();

				// go to a new login page
				LoginUI loginUI = new LoginUI();
			}
		}
	}
}