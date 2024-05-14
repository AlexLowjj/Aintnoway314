package control;

import java.util.List;

import entity.UserProfile;

public class SearchProfileController {
	private UserProfile userProfile;

	public SearchProfileController(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public List<String[]> getProfileByName(String searchInput) {
		return userProfile.getProfileByName(searchInput);

	}

}
