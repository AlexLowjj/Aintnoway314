package csit314;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection connect() {
		String url = "jdbc:postgresql://isilo.db.elephantsql.com:5432/dqiuljny";
		String user = "dqiuljny";
		String password = "nD2eahiu-iYX_QL3YMPy0XO8qxKKSYDM";

//		String url = "jdbc:postgresql://isabelle.db.elephantsql.com:5432/lcbhdoyy";
//		String user = "lcbhdoyy";
//		String password = "zsRQw3X3DfozFur5PqPQ2-Cr1WJ4epkS";

		try {
			System.out.println("Trying to connect to the database...");
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}