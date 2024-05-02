package control;

import entity.UserAccount;

public class LoginController {
    private UserAccount userAccount;

    public LoginController(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    
    public String authenticate(String username, String password) {    
        return userAccount.authenticate(username, password);

    }

}
