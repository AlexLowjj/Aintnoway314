package control;

import java.util.List;

import entity.UserProfile;

public class GetProfileInfoByID {
	private UserProfile userProfile;

    public GetProfileInfoByID(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    
    public List<String> getProfileByID(String searchInput) {    
        return userProfile.getProfileByID(searchInput);

    }
}
