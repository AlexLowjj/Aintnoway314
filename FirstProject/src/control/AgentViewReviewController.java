package control;

import java.util.ArrayList;

import entity.Agent;
import entity.UserAccount;

public class AgentViewReviewController {
	private Agent agent;
	
	public AgentViewReviewController(int userId) {
		this.agent = new Agent(userId);
	}
	
	public ArrayList<UserAccount> getMyReviews(){
		return agent.getMyReviews();
	}

}
