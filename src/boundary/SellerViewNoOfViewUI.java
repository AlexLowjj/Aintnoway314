package boundary;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import control.SellerViewNoOfViewController;
import entity.PropertyListing;

public class SellerViewNoOfViewUI extends JFrame {

	private int userId;

	private Hashtable<Integer, PropertyListing> propertyListings;

	private JTable table;
	private JScrollPane sp;

	public SellerViewNoOfViewUI(int userId, int x, int y) {
		// set title
		super("View No. of Views");
		// set user id
		this.userId = userId;
		// set basic setting of the main frame
		setResizable(true);
		setBounds(x + 250, y, 350, 220);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		// set and add components
		setComponents();
		addComponents();
	}

	private void setComponents() {
		// get data
		String[][] data = getData();

		// if returns null, show a message dialog
		if (data == null) {
			JOptionPane.showMessageDialog(SellerViewNoOfViewUI.this, "Data failed to retrieve.", "message",
					JOptionPane.WARNING_MESSAGE);
		} else {
			String[] columnNames = { "Location", "No. of views" };
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
	}

	private void addComponents() {
		add(sp);
	}

	private String[][] getData() {
		// get property listings
		SellerViewNoOfViewController controller = new SellerViewNoOfViewController(userId);
		propertyListings = controller.getNoOfView();

		if (propertyListings == null) {
			return null;
		}

		// data to be returned
		String[][] data = new String[propertyListings.size()][2];

		// for each property listings
		int keyIndex = 0;
		Enumeration<Integer> enumeration = propertyListings.keys();

		while (enumeration.hasMoreElements()) {
			int key = enumeration.nextElement();

			data[keyIndex][0] = propertyListings.get(key).getLocation();
			data[keyIndex][1] = String.valueOf(propertyListings.get(key).getView());

			keyIndex++;
		}

		return data;
	}
}