package control;

import java.util.List;

import entity.UserAccount;

public class GetUserInfoByID {
	private UserAccount userAccount;

    public GetUserInfoByID(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    
    public List<String> getUserInfoByID(String userid) {    
        return userAccount.getUserInfoByID(userid);

    }

}
