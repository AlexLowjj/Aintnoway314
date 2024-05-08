package control;

import java.util.Hashtable;

import entity.Seller;
import entity.PropertyListing;

public class SellerViewNoOfViewController {
	private Seller seller;

	public SellerViewNoOfViewController(int userId) {
		seller = new Seller(userId);
	}

	public Hashtable<Integer, PropertyListing> getNoOfView() {
		return seller.getNoOfView();
	}
}