package entity;

import csit314.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserAccount {
	private String email, password, newPassword, username, name, phone, description, userType, filename, searchInput;
	private int userid;
	private int rating;
	private String review;
	private boolean is_suspended;

	// Constructor
	// Constructor for getAllUser
	public UserAccount() {
	}

	// Constructor for getUserByEmail, getUserInfoByID, suspendUser
	public UserAccount(String searchInput) {
		this.searchInput = searchInput;
	}

	// Constructor for authenticate
	public UserAccount(String email, String password) {
		this.email = email;
		this.password = password;
	}

	// Constructor for resetPassword
	public UserAccount(String email, String password, String newPassword) {
		this.email = email;
		this.password = password;
		this.newPassword = newPassword;
	}

	// Constructor for createNewUser
	public UserAccount(String username, String email, String name, String phone, String description, String password,
			String userType, String filename) {
		this.username = username;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.description = description;
		this.password = password;
		this.userType = userType;
		this.filename = filename;
	}

	// Constructor for updateUser
	public UserAccount(String username, String password, String email, boolean is_suspended, String userType,
			int userid, String name, String description, String filename, String phone) {
		this.userid = userid;
		this.username = username;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.description = description;
		this.password = password;
		this.userType = userType;
		this.filename = filename;
		this.is_suspended = is_suspended;
	}

	// constructor for AgentViewRating
	public UserAccount(String username, int rating) {
		this.username = username;
		this.rating = rating;
	}

	// constructor for AgentViewReview
	public UserAccount(int noUse, String username, String review) {
		this.username = username;
		this.review = review;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public boolean isIs_suspended() {
		return is_suspended;
	}

	public void setIs_suspended(boolean is_suspended) {
		this.is_suspended = is_suspended;
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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public int getRating() {
		return rating;
	}

	public String getReview() {
		return review;
	}

	public boolean suspendUser(String searchInput) {
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return false;
		}
		String sql = "UPDATE Users SET is_suspended = True WHERE userid = ?;";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			int tempID = Integer.parseInt(searchInput);
			pstmt.setInt(1, tempID);

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

	public List<String[]> getUserByEmail(String searchInput) {
		List<String[]> users = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return null;
		}

		String sql = "SELECT userid, username, email, usertype, is_suspended FROM \"public\".\"users\" WHERE email LIKE ? ORDER BY userid";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + searchInput + "%"); // use method parameter here

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String[] user = new String[5];
					user[0] = rs.getString("userid");
					user[1] = rs.getString("username");
					user[2] = rs.getString("email");
					user[3] = rs.getString("usertype");
					String temp = rs.getString("is_suspended");
					if ("t".equals(temp)) {
						temp = "suspended";
					} else if ("f".equals(temp)) {
						temp = "";
					} else {
						temp = "no data founded";
					}
					user[4] = temp;
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

	public String createNewUser(String username, String email, String name, String phone, String description,
			String password, String userType, String filename) {
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			return "Unable to connect to the database.";
		}

		String sql = "BEGIN;\r\n" + "INSERT INTO \"public\".\"users\" (username, password, email, userType) \r\n"
				+ "VALUES (?,?,?,?);\r\n" + "INSERT INTO \"public\".\"profile\" (userID)\r\n"
				+ "SELECT userid FROM \"public\".\"users\" WHERE email = ?;\r\n" + "UPDATE \"public\".\"profile\" \r\n"
				+ "SET name = ?, description = ?, phone = ?, photo = ?\r\n"
				+ "WHERE userid = (SELECT userid FROM \"public\".\"users\" WHERE email = ?);\r\n" + "COMMIT;";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Set parameters for the INSERT INTO users
			pstmt.setString(1, this.username);
			pstmt.setString(2, this.password);
			pstmt.setString(3, this.email);
			pstmt.setString(4, this.userType);

			// Set parameter for the SELECT in the INSERT INTO profile
			pstmt.setString(5, this.email);

			// parameters for UPDATE profile
			pstmt.setString(6, this.name);
			pstmt.setString(7, this.description);
			pstmt.setString(8, this.phone);
			pstmt.setString(9, this.filename);

			// parameter for WHERE clause
			pstmt.setString(10, this.email);

			int result = pstmt.executeUpdate();
			if (result == 0) {
				return "User had Created Successfully";
			}
		} catch (SQLException e) {
			String msg = e.getMessage();
			if (msg.contains("username")) {
				return "Username already exists.";
			} else if (msg.contains("email")) {
				return "Email already exists.";
			} else if (msg.contains("phone")) {
				return "Phone number already exists.";
			}
			System.out.println("test" + msg);
			return msg;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.err.println("Error closing resources: " + e.getMessage());
			}
		}
		return "Failed to create user";
	}

	public String updateUser(String username, String password, String email, boolean is_suspended, String userType,
			int userid, String name, String description, String filename, String phone) {
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			return "Unable to connect to the database.";
		}

		String sql = "BEGIN;\r\n" + "\r\n" + "UPDATE Users \r\n" + "SET username = ?, \r\n" + "    password = ?, \r\n"
				+ "    email = ?, \r\n" + "    is_suspended = ?, \r\n" + "    userType = ? \r\n"
				+ "WHERE userid = ?;\r\n" + "\r\n" + "UPDATE Profile \r\n" + "SET name = ?, \r\n"
				+ "    description = ?, \r\n" + "    phone = ?, \r\n" + "    photo = ? \r\n" + "WHERE userid = ?;\r\n"
				+ "\r\n" + "COMMIT;";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// parameters for UPDATE users
			pstmt.setString(1, this.username);
			pstmt.setString(2, this.password);
			pstmt.setString(3, this.email);
			pstmt.setBoolean(4, this.is_suspended);
			pstmt.setString(5, this.userType);
			pstmt.setInt(6, this.userid);

			// parameters for UPDATE profile
			pstmt.setString(7, this.name);
			pstmt.setString(8, this.description);
			pstmt.setString(9, this.phone);
			pstmt.setString(10, this.filename);
			pstmt.setInt(11, this.userid);

			int result = pstmt.executeUpdate();
			if (result == 0) {
				return "User had Update Successfully";
			}
		} catch (SQLException e) {
			String msg = e.getMessage();
			if (msg.contains("username")) {
				return "Username already exists.";
			} else if (msg.contains("email")) {
				return "Email already exists.";
			} else if (msg.contains("phone")) {
				return "Phone number already exists.";
			}
			System.out.println("test" + msg);
			return msg;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("Error closing the database connection: " + e.getMessage());
			}
		}
		return "Failed to update User";
	}

	public boolean resetPassword(String email, String password, String newPassword) {
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return false;
		}

		String selectSql = "SELECT userid FROM \"public\".\"users\" WHERE email = ? AND password = ?";
		try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
			selectPstmt.setString(1, this.email);
			selectPstmt.setString(2, this.password);

			try (ResultSet rs = selectPstmt.executeQuery()) {
				if (rs.next()) {
					String updateSql = "UPDATE \"public\".\"users\" SET password = ? WHERE email = ?";
					try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
						updatePstmt.setString(1, this.newPassword);
						updatePstmt.setString(2, this.email);
						int affectedRows = updatePstmt.executeUpdate();
						return affectedRows > 0;
					}
				}
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
		return false;
	}

	public List<String> getUserInfoByID(String searchInput) {
		List<String> userInfo = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return userInfo;
		}

		String sql = "SELECT u.userid, u.username, u.password, u.email, u.is_suspended, u.userType, "
				+ "p.name, p.description, p.photo, p.phone "
				+ "FROM Users u LEFT JOIN Profile p ON u.userid = p.userid " + "WHERE u.userid = ? ORDER BY u.userid";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			int userID = Integer.parseInt(searchInput);
			pstmt.setInt(1, userID);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					userInfo.add(rs.getString("userid"));
					userInfo.add(rs.getString("username"));
					userInfo.add(rs.getString("password"));
					userInfo.add(rs.getString("email"));
					userInfo.add(rs.getString("is_suspended"));
					userInfo.add(rs.getString("userType"));
					userInfo.add(rs.getString("name"));
					userInfo.add(rs.getString("description"));
					userInfo.add(rs.getString("photo"));
					userInfo.add(rs.getString("phone"));
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
		return userInfo;
	}

	public String authenticate(String email, String password) {
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.err.println("Unable to connect to the database.");
			return "db connect problem";
		}

		String sql = "SELECT userid, is_suspended, usertype FROM \"public\".\"users\" WHERE email = ? AND password = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					if (rs.getBoolean("is_suspended")) {
						return "User account is suspended.";
					}
					// choice 1
					String idAndType = String.valueOf(rs.getInt("userid")) + "-" + rs.getString("usertype");
					return idAndType;
				} else {
					return "No user found with the given credentials.";
				}
			}
		} catch (SQLException e) {
			System.err.println("Database error: " + e.getMessage());
			return "login problem";
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.err.println("Error closing resources: " + e.getMessage());
			}
		}
	}

	public List<String[]> getAllUsers() {
		List<String[]> users = new ArrayList<>();
		Connection conn = ConnectDB.connect();
		if (conn == null) {
			System.out.println("Unable to connect to the database.");
			return users;
		}

		String sql = "SELECT userid, username, email, usertype, is_suspended FROM \"public\".\"users\" ORDER BY  userid";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				String[] user = new String[5];
				user[0] = rs.getString("userid");
				user[1] = rs.getString("username");
				user[2] = rs.getString("email");
				user[3] = rs.getString("usertype");
				String temp = rs.getString("is_suspended");
				if ("t".equals(temp)) {
					temp = "suspended";
				} else if ("f".equals(temp)) {
					temp = "";
				} else {
					temp = "no data founded";
				}
				user[4] = temp;
				users.add(user);
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
		return users;
	}

}
