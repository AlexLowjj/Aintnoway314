package control;

import entity.Buyer;
import entity.Agent;

import java.util.List;

public class BuyerContactAgentController {
	private final Buyer buyer;

	public BuyerContactAgentController(int userId) {
		this.buyer = new Buyer(userId);
	}

	public List<Agent> getAgentContacts() {
		return buyer.getAgentContacts();
	}
}