package control;

import entity.UserAccount;

public class ResetPasswordController {
	private UserAccount userAccount;

    public ResetPasswordController(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    
    public boolean resetPassword(String email, String password, String newPassword) {    
        return userAccount.resetPassword(email, password, newPassword);

    }

}
