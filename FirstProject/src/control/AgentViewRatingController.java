package control;

import java.util.ArrayList;
import java.util.Hashtable;

import entity.Agent;
import entity.UserAccount;

public class AgentViewRatingController {
	private Agent agent;
	
	public AgentViewRatingController(int userId) {
		this.agent = new Agent(userId);
	}
	
	public ArrayList<UserAccount> getMyRatings(){
		return agent.getMyRatings();
	}
}
