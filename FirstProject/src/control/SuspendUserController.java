package control;

import entity.UserAccount;

public class SuspendUserController {
private UserAccount userAccount;
	
	public SuspendUserController(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	public boolean suspendUser(String searchInput) {
		return userAccount.suspendUser(searchInput);
    }
}
