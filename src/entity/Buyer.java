package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import csit314.ConnectDB;

public class Buyer {
	private int userId;

	public Buyer(int userId) {
		this.userId = userId;
	}

	public ArrayList<PropertyListing> getPropertyListings() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PropertyListing> propertyListings = null;

		try {
			// connect to DB
			connection = ConnectDB.connect();
			if (connection == null) {
				System.out.println("Unable to connect to database.");
				return null;
			}

			// ArrayList to be returned
			propertyListings = new ArrayList<>();

			// prepare query
			String sql = "SELECT location, type, pricing, status " + "FROM properties;";
			ps = connection.prepareStatement(sql);

			// execute query and get ResultSet
			rs = ps.executeQuery();

			// check if the buyer has an agent
			if (rs.next()) {
				PropertyListing propertyListing = new PropertyListing(rs.getString("location"), rs.getString("type"),
						rs.getInt("pricing"), rs.getString("status"));
				propertyListings.add(propertyListing);

				while (rs.next()) {
					propertyListing = new PropertyListing(rs.getString("location"), rs.getString("type"),
							rs.getInt("pricing"), rs.getString("status"));
					propertyListings.add(propertyListing);
				}

			} else {
				return null;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		} finally {
			// close connection
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
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

		return propertyListings;
	}

	public List<Agent> getAgentContacts() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Agent> agents = null;

		try {
			// connect to DB
			connection = ConnectDB.connect();
			if (connection == null) {
				System.out.println("Unable to connect to database.");
				return null;
			}

			// List to be returned
			agents = new ArrayList<>();

			// prepare query
			String sql = "SELECT users.username, phone FROM users INNER JOIN profile ON users.userid = profile.userid "
					+ "WHERE usertype = 'Agent';";
			ps = connection.prepareStatement(sql);

			// execute query and get ResultSet
			rs = ps.executeQuery();

			if (rs.next()) {
				// Extract agent details from the ResultSet
				String username = rs.getString("username");
				String phone = rs.getString("phone");

				// Create an AgentContact object representing the agent
				Agent agent = new Agent(username, phone);

				// Add the agent to the list
				agents.add(agent);

				while (rs.next()) {
					username = rs.getString("username");
					phone = rs.getString("phone");

					agent = new Agent(username, phone);

					agents.add(agent);
				}

			} else {
				return null;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		} finally {
			// close connection
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
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

		return agents;
	}

	public ArrayList<Agent> getMyAgents() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Agent> agents = null;

		try {
			// connect to DB
			connection = ConnectDB.connect();
			if (connection == null) {
				System.out.println("Unable to connect to database.");
				return null;
			}

			// ArrayList to be returned
			agents = new ArrayList<>();

			// prepare query
			String sql = "SELECT DISTINCT properties.agentid, users.username "
					+ "FROM properties INNER JOIN users ON properties.agentid = users.userid;";
			ps = connection.prepareStatement(sql);

			// execute query and get ResultSet
			rs = ps.executeQuery();

			// check if the buyer has an agent
			if (rs.next()) {
				Agent agent = new Agent(rs.getInt("agentid"), rs.getString("username"));
				agents.add(agent);
				while (rs.next()) {
					agent = new Agent(rs.getInt("agentid"), rs.getString("username"));
					agents.add(agent);
				}
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		} finally {
			// close connection
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
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

		return agents;
	}

	public boolean rateAgent(int agentId, int rating) {
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			// connect to DB
			connection = ConnectDB.connect();
			if (connection == null) {
				System.out.println("Unable to connect to database.");
				return false;
			}

			String sql = "INSERT INTO rating (rating, userid, agentuserid) VALUES (?, ?, ?);";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, rating);
			ps.setInt(2, userId);
			ps.setInt(3, agentId);

			if (ps.executeUpdate() == 1) {
				return true;
			}

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
		}

		return false;
	}

	public boolean reviewAgent(int agentId, String review) {
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			// connect to DB
			connection = ConnectDB.connect();
			if (connection == null) {
				System.out.println("Unable to connect to database.");
				return false;
			}

			String sql = "INSERT INTO review (userid, agentuserid, review) VALUES (?, ?, ?);";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, agentId);
			ps.setString(3, review);

			if (ps.executeUpdate() == 1) {
				return true;
			}

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
		}

		return false;
	}
}