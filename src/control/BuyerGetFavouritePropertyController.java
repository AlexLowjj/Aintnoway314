package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerGetFavouritePropertyController {
private BuyerPropertyListing buyerPropertyListing;
	
	public BuyerGetFavouritePropertyController(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public List<String[]> getFavouriteProperty(String userid){
        return buyerPropertyListing.getFavouriteProperty(userid);
    }
}
