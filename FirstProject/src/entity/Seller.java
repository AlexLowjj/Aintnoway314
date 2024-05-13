package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import csit314.ConnectDB;

public class Seller {
	private int userId;
	private Hashtable<Integer, PropertyListing> propertyListings;

	public Seller(int userId) {
		this.userId = userId;
	}

	public Hashtable<Integer, PropertyListing> getNoOfView() {
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

			// Hashtable to be returned
			propertyListings = new Hashtable<>();

			// set query
			// get propertyid, location, timeview
			String sql = "SELECT propertyid, location, timeview " + "FROM properties " + "WHERE sellerid = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);

			// execute query and get result set
			rs = ps.executeQuery();
			while (rs.next()) {
				PropertyListing property = new PropertyListing();
				property.setPropertyId(rs.getInt("propertyid"));
				property.setLocation(rs.getString("location"));
				property.setView(rs.getInt("timeview"));

				propertyListings.put(property.getPropertyId(), property);
			}

			return propertyListings;

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

	public Hashtable<Integer, PropertyListing> getNoOfShortlisted() {
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

			// Hashtable to be returned
			propertyListings = new Hashtable<>();

			// set query
			// get propertyid, location, count of being shortlisted
			String sql = "SELECT properties.propertyid, properties.location, COUNT(*) AS count "
					+ "FROM properties INNER JOIN favorites " + "ON properties.propertyid = favorites.propertyid "
					+ "WHERE sellerid = ? " + "GROUP BY properties.propertyid;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);

			// execute query and get result set
			rs = ps.executeQuery();
			while (rs.next()) {
				PropertyListing property = new PropertyListing();
				property.setPropertyId(rs.getInt("propertyid"));
				property.setLocation(rs.getString("location"));
				property.setShortlisted(rs.getInt("count"));

				propertyListings.put(property.getPropertyId(), property);
			}

			return propertyListings;

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
					+ "FROM properties INNER JOIN users ON properties.agentid = users.userid "
					+ "WHERE properties.sellerid = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);

			// execute query and get ResultSet
			rs = ps.executeQuery();

			// check if the seller has an agent
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