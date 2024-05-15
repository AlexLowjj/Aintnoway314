package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerViewPropertyByCharController {
private BuyerPropertyListing buyerPropertyListing;
	
	public BuyerViewPropertyByCharController(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public List<String[]> getPropertyByLocationAndType(String location, String type){
        return buyerPropertyListing.getPropertyByLocationAndType(location, type);
    }
}
