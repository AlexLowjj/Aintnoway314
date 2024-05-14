package control;

import entity.Buyer;

public class BuyerRateAgentController {
	private Buyer buyer;

	public BuyerRateAgentController(int userId) {
		this.buyer = new Buyer(userId);
	}

	public boolean rateAgent(int agentId, int rating) {
		return buyer.rateAgent(agentId, rating);
	}
}