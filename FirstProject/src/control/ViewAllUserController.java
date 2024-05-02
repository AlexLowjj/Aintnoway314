package control;

import java.util.List;

import entity.UserAccount;

public class ViewAllUserController {
	private UserAccount userAccount;
	
	public ViewAllUserController(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	public List<String[]> getAllUsers() {
        return userAccount.getAllUsers();
    }

}
