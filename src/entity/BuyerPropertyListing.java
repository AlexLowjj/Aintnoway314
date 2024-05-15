package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import csit314.ConnectDB;


public class BuyerPropertyListing {
	private String userid, propertyid;
	
	public BuyerPropertyListing() {};
	public BuyerPropertyListing(String userid, String propertyid) {
		this.userid = userid;
		this.propertyid = propertyid;
	}

	public List<String> getPropertyType() {
		List<String> propertyType = new ArrayList<>();
		try (Connection conn = ConnectDB.connect()) {
			if (conn == null) {
				System.out.println("Unable to connect to the database.");
				return propertyType;
			}

			String sql = "SELECT DISTINCT type FROM \"public\".\"properties\" WHERE is_suspended <> true;";
			try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					propertyType.add(rs.getString("type"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
		return propertyType;
	}
	
	public List<String> getLocationType() {
		List<String> locationType = new ArrayList<>();
		try (Connection conn = ConnectDB.connect()) {
			if (conn == null) {
				System.out.println("Unable to connect to the database.");
				return locationType;
			}

			String sql = "SELECT DISTINCT location FROM \"public\".\"properties\" WHERE is_suspended <> true;";
			try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					locationType.add(rs.getString("location"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error: " + e.getMessage());
		}
		return locationType;
	}
	
	public List<String[]> getPropertyByLocationAndType(String location, String type) {
		List<String[]> properties = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return null;
		}
		
		System.out.println(location);
		System.out.println(type);
		String sql = "SELECT description, type, location, pricing, status, timeview FROM properties \r\n"
				+ "WHERE is_suspended <> true AND location LIKE ? AND type LIKE ?\r\n"
				+ "ORDER BY  propertyid;";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + location + "%"); 
			pstmt.setString(2, "%" + type + "%"); 

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String[] property = new String[6];
					property[0] = rs.getString("description");
					property[1] = rs.getString("type");
					property[2] = rs.getString("location");
					property[3] = rs.getString("pricing");
					property[4] = rs.getString("status");
					property[5] = rs.getString("timeview");
					properties.add(property);
				}
				System.out.println(properties);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Error closing the database connection: " + e.getMessage());
			}
		}
		return properties;
	}
	
	public List<String> getPropertyByChar(String type, String location, String pricing, String description) {
	    List<String> propertyInfo = new ArrayList<>();
	    Connection conn = ConnectDB.connect();
	    if (conn == null) {
	        System.out.println("Unable to connect to the database.");
	        return propertyInfo;
	    }

	    String sql = "SELECT p.propertyid, p.photo, p.location, p.description, p.type, p.pricing, p.status, u.username AS AgentName, pf.phone " +
	                 "FROM users u " +
	                 "LEFT JOIN properties p ON u.userid = p.agentid " +
	                 "LEFT JOIN profile pf ON u.userid = pf.userid " +
	                 "WHERE p.type LIKE ? AND p.location LIKE ? AND p.pricing = ? AND p.description LIKE ?;";
	    int PriceInt = Integer.parseInt(pricing);
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, "%" + type + "%");
	        pstmt.setString(2, "%" + location + "%");
	        pstmt.setInt(3, PriceInt);
	        pstmt.setString(4, "%" + description + "%");
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                propertyInfo.add(rs.getString("propertyid"));
	                propertyInfo.add(rs.getString("photo"));
	                propertyInfo.add(rs.getString("location"));
	                propertyInfo.add(rs.getString("description"));
	                propertyInfo.add(rs.getString("type"));
	                propertyInfo.add(rs.getString("pricing"));
	                propertyInfo.add(rs.getString("status"));
	                propertyInfo.add(rs.getString("AgentName"));
	                propertyInfo.add(rs.getString("phone"));
	            }
	            System.out.println(propertyInfo);
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
	    return propertyInfo;
	}

	public List<String[]> getFavouriteProperty(String userid) {
		List<String[]> properties = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return null;
		}
		int useridInt = Integer.parseInt(userid);
		String sql = "SELECT p.description, p.type, p.location, p.pricing, p.status, p.timeview FROM favorites f\r\n"
				+ "LEFT JOIN properties p ON f.propertyid = p.propertyid\r\n"
				+ "WHERE userid = ?;";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, useridInt);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String[] property = new String[6];
					property[0] = rs.getString("description");
					property[1] = rs.getString("type");
					property[2] = rs.getString("location");
					property[3] = rs.getString("pricing");
					property[4] = rs.getString("status");
					property[5] = rs.getString("timeview");
					properties.add(property);
				}
				System.out.println(properties);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Error closing the database connection: " + e.getMessage());
			}
		}
		return properties;
	}
	
	
	public List<String[]> getForSaleProperty( ) {
		List<String[]> properties = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return null;
		}
		
		String sql = "SELECT description, type, location, pricing, status, timeview FROM properties \r\n"
				+ "WHERE is_suspended <> true AND status = 'For Sale'\r\n"
				+ "ORDER BY  propertyid;";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String[] property = new String[6];
					property[0] = rs.getString("description");
					property[1] = rs.getString("type");
					property[2] = rs.getString("location");
					property[3] = rs.getString("pricing");
					property[4] = rs.getString("status");
					property[5] = rs.getString("timeview");
					properties.add(property);
				}
				System.out.println(properties);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Error closing the database connection: " + e.getMessage());
			}
		}
		return properties;
	}
	
	public List<String[]> getSoldProperty( ) {
		List<String[]> properties = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return null;
		}
		
		String sql = "SELECT description, type, location, pricing, status, timeview FROM properties \r\n"
				+ "WHERE is_suspended <> true AND status = 'Sold'\r\n"
				+ "ORDER BY  propertyid;";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String[] property = new String[6];
					property[0] = rs.getString("description");
					property[1] = rs.getString("type");
					property[2] = rs.getString("location");
					property[3] = rs.getString("pricing");
					property[4] = rs.getString("status");
					property[5] = rs.getString("timeview");
					properties.add(property);
				}
				System.out.println(properties);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Error closing the database connection: " + e.getMessage());
			}
		}
		return properties;
	}
	
	
	
	public boolean addFavouriteProperty(String userid, String propertyid) {
		Connection conn = ConnectDB.connect();
		int useridInt = Integer.parseInt(this.userid);
		int propertyidInt = Integer.parseInt(this.propertyid);
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return false;
		}

		System.out.println(conn);

		String sql = "INSERT INTO favorites(userid,propertyid) VALUES (?,?);";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, useridInt);
			pstmt.setInt(2, propertyidInt);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				return true;
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception: " + e.getMessage());
			return false;
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
	
	
	
	public List<String[]> getAllProperty() {
		List<String[]> properties = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return properties;
		}

		String sql = "SELECT description, type, location, pricing, status, timeview FROM \"public\".\"properties\" WHERE is_suspended <> true ORDER BY  propertyid";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				String[] property = new String[6];
				property[0] = rs.getString("description");
				property[1] = rs.getString("type");
				property[2] = rs.getString("location");
				property[3] = rs.getString("pricing");
				property[4] = rs.getString("status");
				property[5] = rs.getString("timeview");
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
