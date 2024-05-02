package control;

import entity.UserProfile;

public class CreateProfileController {
private UserProfile userProfile;
	
	public CreateProfileController(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	public boolean createNewProfile(String name, String description) {
		return userProfile.createNewProfile(name, description);
    }
}
