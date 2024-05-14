package control;

import entity.Buyer;

public class BuyerReviewAgentController {
	private Buyer buyer;

	public BuyerReviewAgentController(int userId) {
		this.buyer = new Buyer(userId);
	}

	public boolean reviewAgent(int agentId, String review) {
		return buyer.reviewAgent(agentId, review);
	}
}