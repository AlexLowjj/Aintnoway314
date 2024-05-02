package control;

import entity.PropertyListing;

public class CreatePropertyController {
private PropertyListing propertyListing;
	
	public CreatePropertyController(PropertyListing propertyListing) {
		this.propertyListing = propertyListing;
	}
	
	public String createNewProfile(String sellerid, String agentid, String photo, String location, String description, String type, String pricing, String status) {
		return propertyListing.createNewProperty(sellerid, agentid, photo, location, description, type, pricing,status);
    }
}
