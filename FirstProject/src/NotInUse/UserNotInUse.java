package NotInUse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import csit314.ConnectDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// the parent class for all the user profiles
public class UserNotInUse {
	private int id;
	private String username;
	private String password;
	private String email;
	private String status;
	
	public UserNotInUse() {
		id = -1;
		username = null;
		password = null;
		email = null;
		status = null;
	}

	public UserNotInUse(int id, String username, String password, String email, String status) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.status = status;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	 
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}

	public String getStatus() {
		return status;
	}
	
		
	public static UserNotInUse getUserFromDB(String enteredEmail, String enteredPassword) {
		UserNotInUse user = new UserNotInUse();
        Connection connection = ConnectDB.connect();

        if (connection != null) {
            try {
            	PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"public\".\"temp\" WHERE email=? AND password=?");

            	ps.setString(1, enteredEmail);
            	ps.setString(2, enteredPassword);

            	ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    user.setId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                }
				System.out.println("User ID: " + user.getId() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Password: " + user.getPassword());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println("Failed to make connection!");
        }

        return user;
	}
}
