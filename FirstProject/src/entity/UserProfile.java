package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import csit314.ConnectDB;

public class UserProfile {
	private String name, description, searchInput;
	private int pdid;
    private boolean is_suspended;
    
    //constructor
    //constructor for getAllProfile, getUserTypes
    public UserProfile() {}
    //constructor for getProfileByName,getProfileByEmail
    public UserProfile(String searchInput) {
		this.searchInput = searchInput;
	}
    //constructor for createNewProfile
	public UserProfile(String name, String description) {
		this.name = name;
		this.description = description;
	}
	//constructor for updateProfile
	public UserProfile(String name, String description, int pdid, boolean is_suspended) {
		this.name = name;
		this.description = description;
		this.pdid = pdid;
		this.is_suspended = is_suspended;	
	}
	
	public String getName() { return name; } 
	public void setName(String name) { this.name = name; }

	public String getDescription() { return description; } 
	public void setDescription(String description) { this.description = description; }

	public String getSearchInput() { return searchInput; } 
	public void setSearchInput(String searchInput) { this.searchInput = searchInput; }

	public int getPdid() { return pdid; } 
	public void setPdid(int pdid) { this.pdid = pdid; }

	public boolean isIs_suspended() { return is_suspended; } 
	public void setIs_suspended(boolean is_suspended) { this.is_suspended = is_suspended; }

	
	public boolean suspendProfile(String searchInput) {
        Connection conn = ConnectDB.connect();
        if (conn == null) {
            System.out.println("Unable to connect to the database.");
            return false;
        }
        String sql = "UPDATE profiledescription SET is_suspended = True WHERE pdid = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pdid = Integer.parseInt(searchInput);
            pstmt.setInt(1, pdid); 

            int result = pstmt.executeUpdate();
            System.out.println(result);
            if (result > 0) {
                return true;
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

	
	
	public boolean updateProfile(String name, String description, int pdid, boolean is_suspended) {
        Connection conn = ConnectDB.connect();
        if (conn == null) {
            System.out.println("Unable to connect to the database.");
            return false;
        }
        String sql = "UPDATE profiledescription SET name = ?, description = ?, is_suspended = ? WHERE pdid = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.description);
            pstmt.setBoolean(3, this.is_suspended);
            pstmt.setInt(4, this.pdid); 

            int result = pstmt.executeUpdate();
            if (result > 0) {
                return true;
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
	
	
	public List<String> getProfileByID(String searchInput) {
        List<String> roleInfo = new ArrayList<>();
        Connection conn = ConnectDB.connect();
        if (conn == null) {
            System.out.println("Unable to connect to the database.");
            return roleInfo; 
        }

        String sql = "SELECT pdid,name,description,is_suspended from profiledescription where pdid = ?	ORDER BY pdid";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(searchInput));
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
	
	// to show all the Profile for admin when created a new account or update a profile of a user
	public List<String> getUserTypes() {
        List<String> userTypes = new ArrayList<>();
        try (Connection conn = ConnectDB.connect()) {
            if (conn == null) {
                System.out.println("Unable to connect to the database.");
                return userTypes; 
            }
            
            String sql = "SELECT name FROM \"public\".\"profiledescription\" WHERE is_suspended <> true;";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userTypes.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } 
        return userTypes;
    }
	
	public List<String[]> getProfileByName(String searchInput) {
        List<String[]> users = new ArrayList<>();
        Connection conn = ConnectDB.connect();
        if (conn == null) {
            System.out.println("Unable to connect to the database.");
            return null;
        }
        
        String sql = "SELECT pdid, name, description, is_suspended FROM \"public\".\"profiledescription\" WHERE name LIKE ? ORDER BY  pdid";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchInput + "%"); 
            
            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] user = new String[5];
                    user[0] = rs.getString("pdid");
                    user[1] = rs.getString("name");
                    user[2] = rs.getString("description");
                    String temp = rs.getString("is_suspended");
                    if("t".equals(temp)) {
                        temp = "suspended";
                    } else if ("f".equals(temp)) {
                        temp = "";
                    } else {
                        temp = "no data founded";
                    }
                    user[3] = temp;
                    users.add(user);
                }
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
        return users;
    }



	public boolean createNewProfile(String name, String description) {
        Connection conn = ConnectDB.connect();
        
        if (conn == null) {
            System.out.println("Unable to connect to the database.");
            return false;
        }
        
        System.out.println(conn);
        
        String sql = "INSERT INTO ProfileDescription(name,description) VALUES (?,?);";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);  
            pstmt.setString(2, description); 
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
        return false;  // Corrected to return false by default
    }
	
	
	
	public List<String[]> getAllProfiles() {
        List<String[]> users = new ArrayList<>();
        Connection conn = ConnectDB.connect();
        if (conn == null) {
            System.out.println("Unable to connect to the database.");
            return users;
        }
        
        String sql = "SELECT pdid, name, description, is_suspended FROM \"public\".\"profiledescription\" ORDER BY  pdid";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String[] user = new String[4];
                user[0] = rs.getString("pdid");
                user[1] = rs.getString("name");
                user[2] = rs.getString("description");
                String temp = rs.getString("is_suspended");
                if("t".equals(temp)) {
                    temp = "suspended";
                } else if ("f".equals(temp)) {
                    temp = "";
                } else {
                    temp = "no data founded";
                }
                user[3] = temp;
                
                users.add(user);
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
        return users;
    }





}
