package control;

import entity.Seller;

public class SellerRateAgentController {
	private Seller seller;

	public SellerRateAgentController(int userId) {
		this.seller = new Seller(userId);
	}

	public boolean rateAgent(int agentId, int rating) {
		return seller.rateAgent(agentId, rating);
	}

}
