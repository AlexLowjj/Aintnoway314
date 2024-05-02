package control;

import entity.UserAccount;

public class CreateUserController {
private UserAccount userAccount;
	
	public CreateUserController(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	public String createNewUser(String username, String email, String name, String phone, String description, String password, String userType, String filename) {
		return userAccount.createNewUser(username, email, name, phone, description, password, userType, filename);
    }
	
	

}
