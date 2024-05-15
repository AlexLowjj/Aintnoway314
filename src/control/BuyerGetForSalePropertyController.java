package control;

import java.util.List;

import entity.BuyerPropertyListing;

public class BuyerGetForSalePropertyController {
private BuyerPropertyListing buyerPropertyListing;
	
	public  BuyerGetForSalePropertyController(BuyerPropertyListing buyerPropertyListing) {
		this.buyerPropertyListing = buyerPropertyListing;
	}
	
	public List<String[]> getForSaleProperty(){
        return buyerPropertyListing.getForSaleProperty();
    }
}

