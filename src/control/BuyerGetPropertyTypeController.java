package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerGetPropertyTypeController {
private BuyerPropertyListing buyerPropertyListing;
	
	public  BuyerGetPropertyTypeController(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public List<String> getPropertyType(){
        return buyerPropertyListing.getPropertyType();
    }
}
