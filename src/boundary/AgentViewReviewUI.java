package boundary;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.AgentViewReviewController;
import entity.UserAccount;

public class AgentViewReviewUI extends JFrame {

	private int userId;

	private JTable table;
	private JScrollPane sp;

	public AgentViewReviewUI(int userId, int x, int y) {
		super("View My Reviews");
		this.userId = userId;
		setResizable(true);
		setBounds(x + 400, y, 350, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		setComponents();
	}

	private void setComponents() {
		// get data
		String[][] data = getData();

		// if returns null, show a message dialog
		if (data == null) {
			JOptionPane.showMessageDialog(AgentViewReviewUI.this, "Data failed to retrieve.", "message",
					JOptionPane.WARNING_MESSAGE);
		} else {
			String[] columnNames = { "User Name", "Review" };
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
		AgentViewReviewController controller = new AgentViewReviewController(userId);
		ArrayList<UserAccount> reviews = controller.getMyReviews();

		if (reviews == null) {
			return null;
		}

		// initialize data to be returned
		String[][] data = new String[reviews.size()][2];

		int index = 0;
		for (UserAccount review : reviews) {
			data[index][0] = review.getUsername();
			data[index][1] = String.valueOf(review.getReview());

			index++;
		}

		return data;
	}
}