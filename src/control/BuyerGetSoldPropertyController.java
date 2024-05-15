package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerGetSoldPropertyController {
private BuyerPropertyListing buyerPropertyListing;
	
	public  BuyerGetSoldPropertyController(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public List<String[]> getSoldProperty(){
        return buyerPropertyListing.getSoldProperty();
    }
}

