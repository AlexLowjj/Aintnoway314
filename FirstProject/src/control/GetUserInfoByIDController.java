package control;

import java.util.List;

import entity.UserAccount;

public class GetUserInfoByIDController {
	private UserAccount userAccount;

    public GetUserInfoByIDController(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    
    public List<String> getUserInfoByID(String userid) {    
        return userAccount.getUserInfoByID(userid);

    }

}
