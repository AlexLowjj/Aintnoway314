package csit314;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import csit314.ConnectDB;

public class ExampleClass {
    public static void main(String[] args) {
        Connection connection = ConnectDB.connect();
        if (connection != null) {
            try {
                String query = "SELECT * FROM \"public\".\"users\"";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String password = rs.getString("password"); // Be cautious with handling real passwords

                    System.out.println("User ID: " + userId + ", Username: " + username + ", Email: " + email + ", Password: " + password);
                }
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
    }
}
