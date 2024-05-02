package csit314;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    public static void main(String[] args) {
        // Connection details
    	String url = "jdbc:postgresql://isilo.db.elephantsql.com:5432/dqiuljny";
    	String user = "dqiuljny";
    	String password = "nD2eahiu-iYX_QL3YMPy0XO8qxKKSYDM"; // Use your actual password


        try {
        	System.out.println("try to connect");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
    public static Connection connect() {
        String url = "jdbc:postgresql://isilo.db.elephantsql.com:5432/dqiuljny";
        String user = "dqiuljny";
        String password = "nD2eahiu-iYX_QL3YMPy0XO8qxKKSYDM"; // Use your actual password

        try {
            System.out.println("Trying to connect to the database...");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

