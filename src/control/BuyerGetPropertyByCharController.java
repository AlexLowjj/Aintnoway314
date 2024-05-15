package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerGetPropertyByCharController {
private BuyerPropertyListing buyerPropertyListing;
	
	public  BuyerGetPropertyByCharController(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public List<String> getPropertyByChar(String description, String type, String location, String pricing){
        return buyerPropertyListing.getPropertyByChar(description, type, location, pricing);
    }
}
