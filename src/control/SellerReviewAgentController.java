package control;

import entity.Seller;

public class SellerReviewAgentController {
	private Seller seller;

	public SellerReviewAgentController(int userId) {
		this.seller = new Seller(userId);
	}

	public boolean reviewAgent(int agentId, String review) {
		return seller.reviewAgent(agentId, review);
	}
}