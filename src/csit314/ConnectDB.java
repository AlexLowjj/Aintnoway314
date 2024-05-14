package csit314;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection connect() {
		String url = "jdbc:postgresql://isilo.db.elephantsql.com:5432/dqiuljny";
		String user = "dqiuljny";
		String password = "nD2eahiu-iYX_QL3YMPy0XO8qxKKSYDM";

		try {
			System.out.println("Trying to connect to the database...");
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}