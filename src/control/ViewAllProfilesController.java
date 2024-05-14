package control;

import java.util.List;

import entity.UserProfile;

public class ViewAllProfilesController {
	private UserProfile userProfile;

	public ViewAllProfilesController(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public List<String[]> getAllProfiles() {
		return userProfile.getAllProfiles();
	}

}
