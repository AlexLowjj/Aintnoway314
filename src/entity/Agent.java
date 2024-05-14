package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import csit314.ConnectDB;

public class Agent {

	private int userId;
	private String username;
	private String email;

	public Agent(int userId) {
		this.userId = userId;
	}

	public Agent(int userId, String username) {
		this.userId = userId;
		this.username = username;
	}

	public Agent(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public ArrayList<UserAccount> getMyRatings() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// connect to DB
			connection = ConnectDB.connect();
			if (connection == null) {
				System.out.println("Unable to connect to database.");
				return null;
			}

			// initialize ArrayList to be returned
			ArrayList<UserAccount> ratings = new ArrayList<>();

			// set query
			// get user names and ratings
			String sql = "SELECT users.username, rating.rating " + "FROM rating INNER JOIN users "
					+ "ON rating.userid = users.userid " + "WHERE rating.agentuserid = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);

			// execute query and get result set
			rs = ps.executeQuery();
			// add ratings to ArrayList
			while (rs.next()) {
				ratings.add(new UserAccount(rs.getString("username"), rs.getInt("rating")));
			}

			return ratings;
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		} finally {
			// close connection
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}

			// close prepared statement
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}

			// close result set
			if (rs != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		// if nothing has been return so far, then return null
		return null;
	}

	public ArrayList<UserAccount> getMyReviews() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// connect to DB
			connection = ConnectDB.connect();
			if (connection == null) {
				System.out.println("Unable to connect to database.");
				return null;
			}

			// initialize ArrayList to be returned
			ArrayList<UserAccount> reviews = new ArrayList<>();

			// set query
			// get user names and reviews
			String sql = "SELECT users.username, review.review " + "FROM review INNER JOIN users "
					+ "ON review.userid = users.userid " + "WHERE review.agentuserid = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);

			// execute query and get result set
			rs = ps.executeQuery();
			// add ratings to ArrayList
			while (rs.next()) {
				reviews.add(new UserAccount(0, rs.getString("username"), rs.getString("review")));
			}

			return reviews;
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		} finally {
			// close connection
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}

			// close prepared statement
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}

			// close result set
			if (rs != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		// if nothing has been return so far, then return null
		return null;
	}
}