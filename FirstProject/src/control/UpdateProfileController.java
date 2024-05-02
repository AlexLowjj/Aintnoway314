package control;

import entity.UserProfile;

public class UpdateProfileController {
private UserProfile userProfile;
	
	public UpdateProfileController(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	public boolean updateProfile(String name, String description, int pdid, boolean is_suspended) {
		return userProfile.updateProfile(name, description, pdid, is_suspended);
    }
}
