package control;

import java.util.List;

import entity.UserAccount;

public class SearchUserController {
	private UserAccount userAccount;

	public SearchUserController(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public List<String[]> getUserByEmail(String searchInput) {
		return userAccount.getUserByEmail(searchInput);

	}

}
