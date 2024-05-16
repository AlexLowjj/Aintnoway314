package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import csit314.ConnectDB;

public class PropertyListing {

	private int propertyId;
	private int view;
	private int shortlisted;
	private String sellerid, agentid, photo, location, description, type, pricing, status, propertyIdHolder;

	public PropertyListing() {
	}

	public PropertyListing(String type, String location, String description, String pricing, String status,
			String photo, String propertyIdHolder) {
		this.type = type;
		this.location = location;
		this.description = description;
		this.pricing = pricing;
		this.status = status;
		this.photo = photo;
		this.propertyIdHolder = propertyIdHolder;
	}

	// constructor for BuyerSelectPropertyListing
	public PropertyListing(String location, String type, int pricing, String status) {
		this.location = location;
		this.type = type;
		this.pricing = status;
		this.status = status;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setView(int view) {
		this.view = view;
	}

	public void setShortlisted(int shortlisted) {
		this.shortlisted = shortlisted;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public String getLocation() {
		return location;
	}

	public int getView() {
		return view;
	}

	public int getShortlisted() {
		return shortlisted;
	}

	public boolean RemoveProperties(String type, String location, String pricing) {
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return false;
		}
		String sql = "UPDATE \"public\".\"properties\"SET is_suspended = True WHERE type = ? AND location = ? AND pricing = ?;";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			System.out.println("Executing update with parameters: " + type + ", " + location + ", " + pricing);
			pstmt.setString(1, type);
			pstmt.setString(2, location);
			pstmt.setInt(3, Integer.parseInt(pricing));

			int result = pstmt.executeUpdate();
			System.out.println("Rows affected: " + result);
			if (result > 0) {
				return true;
			} else {
			}

		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error closing the database connection: " + e.getMessage());
			}
		}
		return false;
	}

	public String createNewProperty(String sellerid, String agentid, String photo, String location, String description,
			String type, String pricing, String status) {
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			return "Unable to connect to the database.";
		}

		String sql = "INSERT INTO properties (sellerid, agentid, photo, location, description, type, pricing, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set parameters for the INSERT INTO properties
			pstmt.setInt(1, Integer.parseInt(sellerid));
			pstmt.setInt(2, Integer.parseInt(agentid));
			pstmt.setString(3, photo);
			pstmt.setString(4, location);
			pstmt.setString(5, description);
			pstmt.setString(6, type);
			pstmt.setInt(7, Integer.parseInt(pricing));
			pstmt.setString(8, status);

			int result = pstmt.executeUpdate();
			if (result > 0) {
				return "Property created successfully.";
			} else {
				return "Failed to create property. No rows affected.";
			}
		} catch (SQLException e) {
			return "SQL Exception: " + e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.err.println("Error closing resources: " + e.getMessage());
			}
		}
	}

	public String updateProperty(String type, String location, String description, String pricing, String status,
			String photo, String propertyIdHolder) {
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			return "Unable to connect to the database.";
		}

		String sql = "UPDATE properties\r\n" + "SET \r\n" + "type = ?,\r\n" + "location = ?,\r\n"
				+ "description = ?,\r\n" + "pricing = ?,\r\n" + "status = ?,\r\n" + "photo = ?\r\n"
				+ "WHERE propertyid = ? ";
		int pricingInt = Integer.parseInt(pricing);
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, this.type);
			pstmt.setString(2, this.location);
			pstmt.setString(3, this.description);
			pstmt.setInt(4, pricingInt);
			pstmt.setString(5, this.status);
			pstmt.setString(6, this.photo);
			pstmt.setInt(7, Integer.parseInt(this.propertyIdHolder));

			int result = pstmt.executeUpdate();
			System.out.println(result);
			if (result == 1) {
				return "Property had updated successfully";
			}
		} catch (SQLException e) {
			return "SQL Exception: " + e.getMessage();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error closing the database connection: " + e.getMessage());
			}
		}
		return "Failed to update Property";
	}

	public List<String> getPropertyByChar(String type, String location, String pricing) {
		List<String> roleInfo = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return roleInfo;
		}

		String sql = "SELECT p.propertyid,p.photo,p.location,p.description,p.type,p.pricing,p.status,p.is_suspended, u.username AS SellerName, u2.username AS AgentName \r\n"
				+ "FROM properties p\r\n" + "LEFT JOIN\r\n" + "users u ON p.sellerid = u.userid\r\n" + "LEFT JOIN\r\n"
				+ "users u2 ON p.agentid = u2.userid\r\n"
				+ "where type = ? and location = ? and pricing = ? ORDER BY propertyid";
		int PriceInt = Integer.parseInt(pricing);
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, type);
			pstmt.setString(2, location);
			pstmt.setInt(3, PriceInt);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					boolean temp = rs.getBoolean("is_suspended");
					if (temp) {
						// to be handle
					} else {
						roleInfo.add(rs.getString("photo"));
						roleInfo.add(rs.getString("location"));
						roleInfo.add(rs.getString("description"));
						roleInfo.add(rs.getString("type"));
						roleInfo.add(rs.getString("pricing"));
						roleInfo.add(rs.getString("status"));
						roleInfo.add(rs.getString("SellerName"));
						roleInfo.add(rs.getString("AgentName"));
						roleInfo.add(rs.getString("propertyid"));
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception: " + e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.err.println("Error closing resources: " + e.getMessage());
			}
		}
		return roleInfo;
	}

	public List<String[]> getAllProperty() {
		List<String[]> properties = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return properties;
		}

		String sql = "SELECT type, location, description, pricing, status FROM \"public\".\"properties\" WHERE is_suspended <> true ORDER BY  propertyid";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				String[] property = new String[5];
				property[0] = rs.getString("type");
				property[1] = rs.getString("location");
				property[2] = rs.getString("description");
				property[3] = rs.getString("pricing");
				property[4] = rs.getString("status");
				properties.add(property);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.err.println("Error closing resources: " + e.getMessage());
			}
		}
		return properties;
	}
}