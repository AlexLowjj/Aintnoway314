package boundary;

import javax.swing.JFrame;
import javax.swing.JPanel;

//import NotInUse.LoginUI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SellerMenuUI extends JFrame {

	private int userId;

	private JPanel viewNoOfViewPanel;
	private HyperLinkButtonUI viewNoOfViewButton;

	private JPanel viewNoOfShortlistedPanel;
	private HyperLinkButtonUI viewNoOfShortlistedButton;

	private JPanel rateAgentPanel;
	private HyperLinkButtonUI rateAgentButton;

	private JPanel reviewAgentPanel;
	private HyperLinkButtonUI reviewAgentButton;

	private JPanel logoutPanel;
	private JButton logoutButton;

	// constructor
	public SellerMenuUI(int userId) {
		// set title
		super("Seller Menu");
		// set user id
		this.userId = userId;
		// specify basic settings for the main frame
		setSize(250, 220);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(5, 1));

		// set components
		setComponents();
		addComponents();

		setVisible(true);
	}

	private void setComponents() {
		// view no of views
		viewNoOfViewPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		viewNoOfViewButton = new HyperLinkButtonUI("> View No. of Views", new Color(16, 9, 192));
		viewNoOfViewButton.setFont(new Font("Dialog", Font.BOLD, 14));

		viewNoOfViewPanel.add(viewNoOfViewButton);

		// view no of being shortlisted
		viewNoOfShortlistedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		viewNoOfShortlistedButton = new HyperLinkButtonUI("> View No. of Being Shortlisted", new Color(16, 9, 192));
		viewNoOfShortlistedButton.setFont(new Font("Dialog", Font.BOLD, 14));

		viewNoOfShortlistedPanel.add(viewNoOfShortlistedButton);

		// rate agents
		rateAgentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		rateAgentButton = new HyperLinkButtonUI("> Rate My Agents", new Color(16, 9, 192));
		rateAgentButton.setFont(new Font("Dialog", Font.BOLD, 14));

		rateAgentPanel.add(rateAgentButton);

		// review agents
		reviewAgentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		reviewAgentButton = new HyperLinkButtonUI("> Review My Agents", new Color(16, 9, 192));
		reviewAgentButton.setFont(new Font("Dialog", Font.BOLD, 14));

		reviewAgentPanel.add(reviewAgentButton);

		// log out
		logoutPanel = new JPanel();
		logoutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		ImageIcon logoutIcon = new ImageIcon("./src/boundary/img/logout.png");
		logoutButton = new JButton(logoutIcon);
		logoutButton.setText("Log out");
		logoutButton.setHorizontalTextPosition(logoutButton.LEFT);
		logoutButton.setBorder(BorderFactory.createEmptyBorder());
		logoutButton.setContentAreaFilled(false);
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		logoutPanel.add(logoutButton);

		// event handler
		ButtonHandler hanlder = new ButtonHandler();
		viewNoOfViewButton.addActionListener(hanlder);
		viewNoOfShortlistedButton.addActionListener(hanlder);
		rateAgentButton.addActionListener(hanlder);
		reviewAgentButton.addActionListener(hanlder);
		logoutButton.addActionListener(hanlder);
	}

	private void addComponents() {
		add(viewNoOfViewPanel);
		add(viewNoOfShortlistedPanel);
		add(rateAgentPanel);
		add(reviewAgentPanel);
		add(logoutPanel);
	}

	private class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == viewNoOfViewButton) {
				SellerViewNoOfViewUI svnovUI = new SellerViewNoOfViewUI(userId, SellerMenuUI.this.getX(),
						SellerMenuUI.this.getY());

			} else if (e.getSource() == viewNoOfShortlistedButton) {
				SellerViewNoOfShortlistedUI svnovUI = new SellerViewNoOfShortlistedUI(userId, SellerMenuUI.this.getX(),
						SellerMenuUI.this.getY());

			} else if (e.getSource() == rateAgentButton) {
				// create new JFrame for selecting agent
				// pass in "rate" as parameter to indicate that the selection is for rating
				SellerSelectAgentUI ssaUI = new SellerSelectAgentUI("rate", userId, SellerMenuUI.this.getX(),
						SellerMenuUI.this.getY());

			} else if (e.getSource() == reviewAgentButton) {
				// create new JFrame for selecting agent
				// pass in "review" as parameter to indicate that the selection is for reviewing
				SellerSelectAgentUI ssaUI = new SellerSelectAgentUI("review", userId, SellerMenuUI.this.getX(),
						SellerMenuUI.this.getY());

			} else if (e.getSource() == logoutButton) {
				// Dispose of the current SellerMenuUI instance
				dispose();
			
				// Show the LoginUI instance
				LoginUI loginUI = new LoginUI();
				loginUI.setVisible(true);
			}
			
		}
	}
}