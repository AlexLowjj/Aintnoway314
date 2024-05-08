package NotInUse;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import boundary.HyperLinkButtonUI;

public class SellerMenuUINotInUse extends JFrame {

	private int userId;

	private JPanel searchPanel;
	private JTextField searchBar;
	private JButton searchButton;
	private JPanel createPanel;
	private HyperLinkButtonUI createButton;
	private JPanel viewPropertyPanel;
	private HyperLinkButtonUI viewPropertyButton;
	private JPanel viewAgentPanel;
	private HyperLinkButtonUI viewAgentButton;
	private JPanel logoutPanel;
	private JButton logoutButton;

	// constructor
	public SellerMenuUINotInUse(int userId) {
		// set title
		super("Seller Menu");
		// set user id
		this.userId = userId;
		// specify basic settings for the main frame
		setSize(290, 230);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// create panels and specify their content
		setSearchBar();
		setCreate();
		setViewProperty();
		setViewAgent();
		setLogout();

		// add event handler
		addHandler();

		// set layout and add components
		getContentPane().setLayout(new GridLayout(5, 1));
		addComponents();

		setVisible(true);
	}

	private void setSearchBar() {
		// set the panel
		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// set other components
		searchBar = new JTextField(20);
		searchBar.setToolTipText("type in address to search");

		ImageIcon searchImg = new ImageIcon("./src/boundary/img/searchIcon.png");
		searchButton = new JButton(searchImg);
		searchButton.setBorder(BorderFactory.createEmptyBorder());
		searchButton.setContentAreaFilled(false);
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// add components to panel
		searchPanel.add(searchBar);
		searchPanel.add(searchButton);
	}

	private void setCreate() {
		// set the panel
		createPanel = new JPanel();
		createPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// set other components
		createButton = new HyperLinkButtonUI("> create property listing", new Color(16, 9, 192));
		createButton.setFont(new Font("Dialog", Font.BOLD, 14));

		// add components to panel
		createPanel.add(createButton);
	}

	private void setViewProperty() {
		// set the panel
		viewPropertyPanel = new JPanel();
		viewPropertyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// set other components
		viewPropertyButton = new HyperLinkButtonUI("> view my property listings", new Color(16, 9, 192));
		viewPropertyButton.setFont(new Font("Dialog", Font.BOLD, 14));

		// add components to panel
		viewPropertyPanel.add(viewPropertyButton);
	}

	private void setViewAgent() {
		// set the panel
		viewAgentPanel = new JPanel();
		viewAgentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// set other components
		viewAgentButton = new HyperLinkButtonUI("> view my agents", new Color(16, 9, 192));
		viewAgentButton.setFont(new Font("Dialog", Font.BOLD, 14));

		// add components to panel
		viewAgentPanel.add(viewAgentButton);
	}

	private void setLogout() {
		// set the panel
		logoutPanel = new JPanel();
		logoutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// set other components
		ImageIcon logoutIcon = new ImageIcon("./src/boundary/img/logout.png");
		logoutButton = new JButton(logoutIcon);
		logoutButton.setToolTipText("Log out");
		logoutButton.setBorder(BorderFactory.createEmptyBorder());
		logoutButton.setContentAreaFilled(false);
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// add components to panel
		logoutPanel.add(logoutButton);
	}

	private void addHandler() {
		ButtonHandler handler = new ButtonHandler();
		createButton.addActionListener(handler);
	}

	private class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == createButton) {
				
			} else if (e.getSource() == viewPropertyButton) {

			}
		}
	}

	// add components to the main frame
	private void addComponents() {
		add(searchPanel);
		add(createPanel);
		add(viewPropertyPanel);
		add(viewAgentPanel);
		add(logoutPanel);
	}
}