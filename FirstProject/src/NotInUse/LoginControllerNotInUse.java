package NotInUse;

import csit314.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginControllerNotInUse {

    public static UserNotInUse authenticate(String email, String password) {
    	UserNotInUse user = UserNotInUse.getUserFromDB(email, password);
    	
    	return user;
    }
}