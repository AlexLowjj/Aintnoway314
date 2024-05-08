package NotInUse;

import java.util.Hashtable;

import entity.PropertyListing;
import entity.Seller;

public class SellerViewPropertyStatsController {
	private Seller seller;

	public SellerViewPropertyStatsController(int userId) {
		this.seller = new Seller(userId);
	}

	//public Hashtable<Integer, PropertyListing> getPropertyStats() {
		//return seller.getPropertyStats();
	//}
}
