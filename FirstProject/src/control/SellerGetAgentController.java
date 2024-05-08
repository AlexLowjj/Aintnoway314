package control;

import java.util.ArrayList;

import entity.Agent;
import entity.Seller;

public class SellerGetAgentController {
	private Seller seller;

	public SellerGetAgentController(int userId) {
		seller = new Seller(userId);
	}

	public ArrayList<Agent> getMyAgents() {
		return seller.getMyAgents();
	}
}
