package boundary;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.AgentViewRatingController;
import entity.UserAccount;

public class AgentViewRatingUI extends JFrame {
	private int userId;

	private JTable table;
	private JScrollPane sp;

	public AgentViewRatingUI(int userId, int x, int y) {
		super("View My Ratings");
		this.userId = userId;
		setResizable(true);
		setBounds(x + 400, y, 300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		setComponents();
	}

	private void setComponents() {
		// get data
		String[][] data = getData();

		// if returns null, show a message dialog
		if (data == null) {
			JOptionPane.showMessageDialog(AgentViewRatingUI.this, "Data failed to retrieve.", "message",
					JOptionPane.WARNING_MESSAGE);
		} else {
			String[] columnNames = { "User Name", "Rating" };
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

			add(sp);
		}
	}

	private String[][] getData() {
		// get ratings
		AgentViewRatingController controller = new AgentViewRatingController(userId);
		ArrayList<UserAccount> ratings = controller.getMyRatings();

		if (ratings == null) {
			return null;
		}

		// initialize data to be returned
		String[][] data = new String[ratings.size()][2];

		int index = 0;
		for (UserAccount rating : ratings) {
			data[index][0] = rating.getUsername();
			data[index][1] = String.valueOf(rating.getRating());

			index++;
		}

		return data;
	}
}