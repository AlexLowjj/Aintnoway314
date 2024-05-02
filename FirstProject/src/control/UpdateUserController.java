package control;

import entity.UserAccount;

public class UpdateUserController {
private UserAccount userAccount;
	
	public UpdateUserController(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	public String updateUser(String username,String password, String email, boolean is_suspended, String userType, int userid, String name, String description, String filename, String phone) {
		return userAccount.updateUser(username,password, email, is_suspended, userType, userid, name, description, filename, phone);
    }
}
