package control;

import java.util.List;

import entity.UserProfile;

public class GetTypeOfProfileController {
	private UserProfile userProfile;
	
	public GetTypeOfProfileController(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	public  List<String> getUserTypes() {
		return userProfile.getUserTypes();
    }
	

}
