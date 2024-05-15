package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerAddFavouriteProperty {
private BuyerPropertyListing buyerPropertyListing;
	
	public  BuyerAddFavouriteProperty(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public boolean addFavouriteProperty(String userid, String propertyid){
        return buyerPropertyListing.addFavouriteProperty(userid, propertyid);
    }
}
