package control;

import java.util.List;

import entity.UserProfile;

public class GetProfileInfoByIDController {
	private UserProfile userProfile;

	public GetProfileInfoByIDController(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public List<String> getProfileByID(String searchInput) {
		return userProfile.getProfileByID(searchInput);

	}
}
