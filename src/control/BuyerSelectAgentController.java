package control;

import java.util.ArrayList;

import entity.Agent;
import entity.Buyer;

public class BuyerSelectAgentController {
	private Buyer buyer;

	public BuyerSelectAgentController(int userId) {
		buyer = new Buyer(userId);
	}

	public ArrayList<Agent> getMyAgents() {
		return buyer.getMyAgents();
	}
}