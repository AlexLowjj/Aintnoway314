package control;

import entity.UserProfile;

public class SuspendProfileController {
private UserProfile userProfile;
	
	public SuspendProfileController(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	public boolean suspendProfile(String searchInput) {
		return userProfile.suspendProfile(searchInput);
    }

}
