package NotInUse;

import csit314.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
	private String email;
	private String password;
	private String chosenType;

	public Login(String email, String password, String chosenType) {
		this.email = email;
		this.password = password;
		this.chosenType = chosenType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String authenticate() {
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return null;
		}

//        String typeID = "";
//        String tableName = "";
//        
//        if( chosenType == "Agent") {
//        	typeID = "AgentID";tableName="Agent"; 	
//        } else if ( chosenType == "Buyer") {
//        	typeID = "buyerID";tableName="Buyer"; 	
//        } else if ( chosenType == "Seller") {
//        	typeID = "sellerID";tableName="Seller"; 	
//        } else if ( chosenType == "Admin") {
//        	typeID = "adminID";tableName="Admin"; 	
//        } else {
//        	//
//        }

//        String sql = "SELECT user_id FROM \"public\".\"users\" WHERE email = ? AND password = ?";
//        String sql = "SELECT " + tableName + "."+typeID+" FROM \"public\".\"" + tableName + "\" JOIN \"public\".\"Users\" ON " + tableName + "."+typeID+" = Users.UserID WHERE Users.email = ? AND Users.password = ?;";

		String sql = "SELECT userid FROM \"public\".\"users\" WHERE email = ? AND password = ? AND usertype = ? AND is_suspended = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, this.email);
			pstmt.setString(2, this.password);
			pstmt.setString(3, this.chosenType);
			pstmt.setBoolean(4, false);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					String userId = rs.getString("userid"); // Assuming 'user_id' is the column name in your database
					System.out.println("Authenticated with user_id: " + userId); // Print user_id to the console
					return userId;
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
		return null;
	}
}
