package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerViewAllPropertiesController {
private BuyerPropertyListing buyerPropertyListing;
	
	public BuyerViewAllPropertiesController(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public List<String[]> getAllProperty() {
        return buyerPropertyListing.getAllProperty();
    }
}
