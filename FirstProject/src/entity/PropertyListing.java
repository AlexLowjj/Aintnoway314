package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import csit314.ConnectDB;

public class PropertyListing {
	
	private String sellerid, agentid, photo, location, description, type, pricing, status;
	
	public String createNewProperty(String sellerid, String agentid, String photo, String location, String description, String type, String pricing, String status) {
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
	
	public List<String> getProfileByChar(String type, String location, String pricing ) {
        List<String> roleInfo = new ArrayList<>();
        Connection conn = ConnectDB.connect();
        if (conn == null) {
            System.out.println("Unable to connect to the database.");
            return roleInfo; 
        }

        String sql = "SELECT photo,location,description,type,pricing,status,is_suspend from properties where type = ? and location = ? and pricing = ? ORDER BY propertyid";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(1, location);
            pstmt.setString(1, pricing);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                	roleInfo.add(rs.getString("pdid"));
                	roleInfo.add(rs.getString("name"));
                	roleInfo.add(rs.getString("description"));
                	roleInfo.add(rs.getString("is_suspended"));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
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
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
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
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        return properties;
    }

}
