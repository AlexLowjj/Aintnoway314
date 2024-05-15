package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerGetLocationController {
private BuyerPropertyListing buyerPropertyListing;
	
	public  BuyerGetLocationController(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public List<String> getLocationType(){
        return buyerPropertyListing.getLocationType();
    }
}
