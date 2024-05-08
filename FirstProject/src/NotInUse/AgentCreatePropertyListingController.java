package NotInUse;

import entity.Agent;

public class AgentCreatePropertyListingController {
	private Agent agent;

	public AgentCreatePropertyListingController(int userId) {
		this.agent = new Agent(userId);
	}

	public boolean createPropertyListing(int sellerId, String purpose, String type, int price, String photo,
			String address, String description) {
		return agent.createPropertyListing(sellerId, purpose, type, price, photo, address, description);
	}
}
