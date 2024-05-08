package NotInUse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sound.midi.Soundbank;

import csit314.ConnectDB;
import entity.PropertyListing;

public class SellerNotInUse {
	private String userId;
	private String username;
	private String password;
	private String email;
	private String status;
	private PropertyListing[] propertyListings;

	public SellerNotInUse(String userId) {
		this.userId = userId;
	}

	public SellerNotInUse(String userId, String username, String password, String email, String status,
			PropertyListing[] propertyListings) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.status = status;
		this.propertyListings = propertyListings;
	}

	// today
	public boolean createPropertyListing(String purpose, String type, int price, String photo, String address,
			String description) {
		// connect to db
		Connection connection = ConnectDB.connect();

		if (connection == null) {
			System.out.println("Unable to connecte to database.");
			return false;
		}

		String sql = "INSERT INTO \"public\".\"properties\" "
				+ "(sellerid, agentid, photo, location, description, type, pricing, status, is_suspended, timeview) "
				+ "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			// set
			
			try {
				ps.executeQuery();
				return true;
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Error closing the database connection: " + e.getMessage());
			}
		}

		return false;
	}
}
