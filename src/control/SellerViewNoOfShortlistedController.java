package control;

import java.util.Hashtable;

import entity.Seller;
import entity.PropertyListing;

public class SellerViewNoOfShortlistedController {
	private Seller seller;

	public SellerViewNoOfShortlistedController(int userId) {
		seller = new Seller(userId);
	}

	public Hashtable<Integer, PropertyListing> getNoOfShortlisted() {
		return seller.getNoOfShortlisted();
	}
}