package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import csit314.ConnectDB;

public class Agent {

	private int userId;
	private String username;

	public Agent(int userId) {
		this.userId = userId;
	}

	public Agent(int userId, String username) {
		this.userId = userId;
		this.username = username;
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public boolean createPropertyListing(int sellerId, String purpose, String type, int price, String photo,
			String address, String description) {
		// connect to DB
		Connection connection = ConnectDB.connect();

		if (connection == null) {
			System.out.println("Unable to connecte to database.");
			return false;
		}

		// prepare sql query
		String sql = "INSERT INTO \"public\".\"properties\" "
				+ "(sellerid, agentid, photo, location, description, type, pricing, status, is_suspended, timeview) "
				+ "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			// set
			ps.setInt(1, sellerId); // sellerid
			ps.setInt(2, userId); // agentid
			ps.setString(3, photo); // photo
			ps.setString(4, address); // location
			ps.setString(5, description); // description
			ps.setString(6, type); // type
			ps.setInt(7, price); // pricing
			ps.setString(8, purpose); // status
			ps.setBoolean(9, false); // is_suspeneded
			ps.setInt(10, 0); // timeview

			int result = ps.executeUpdate();
			if (result == 1) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		} finally {
			try {
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				System.out.println("Error closing the database connection: " + e.getMessage());
			}
		}

		return false;
	}
}
