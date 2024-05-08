package NotInUse;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import entity.PropertyListing;

public class SellerViewPropertyStatsUI extends JFrame {

	private int userId;

	private Hashtable<Integer, PropertyListing> propertyListings;
	
	private JLabel prompt;

	private JTable table;
	private JScrollPane sp;

	public SellerViewPropertyStatsUI(int userId, int x, int y) {
		// set title
		super("View Property Stats");
		// set user id
		this.userId = userId;
		// set basic setting of the main frame
		setResizable(true);
		setBounds(x + 250, y - 60, 500, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		// set and add components
		setComponents();
		addComponents();
	}

	private void setComponents() {
		// create table to display data
		String[] columnNames = { "Location", "No. of views", "No. of being shortlisted" };
		String[][] data = getData();
		table = new JTable(data, columnNames);

		// set table model to make table uneditable
		DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(tableModel);

		// add table to scroll pane
		sp = new JScrollPane(table);
	}

	private void addComponents() {
		add(sp);
	}

	private String[][] getData() {
		// get property listings
		SellerViewPropertyStatsController controller = new SellerViewPropertyStatsController(userId);
		//propertyListings = controller.getPropertyStats();

		// data to be returned
		String[][] data = new String[propertyListings.size()][3];

		// for each property listings
		int keyIndex = 0;
		Enumeration<Integer> enumeration = propertyListings.keys();

		while (enumeration.hasMoreElements()) {
			int key = enumeration.nextElement();

			data[keyIndex][0] = propertyListings.get(key).getLocation();
			data[keyIndex][1] = String.valueOf(propertyListings.get(key).getView());
			data[keyIndex][2] = String.valueOf(propertyListings.get(key).getShortlisted());

			keyIndex++;
		}

		return data;
	}
}
