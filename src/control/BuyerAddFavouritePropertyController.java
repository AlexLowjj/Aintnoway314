package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerAddFavouritePropertyController {
private BuyerPropertyListing buyerPropertyListing;
	
	public  BuyerAddFavouritePropertyController(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public boolean addFavouriteProperty(String userid, String propertyid){
        return buyerPropertyListing.addFavouriteProperty(userid, propertyid);
    }
}
