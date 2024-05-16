package control;

import entity.PropertyListing;

public class RemovePropertyController {
	private PropertyListing propertyListing;

	public RemovePropertyController(PropertyListing propertyListing) {
		this.propertyListing = propertyListing;
	}

	public boolean RemoveProperties(String type, String location, String pricing) {
		return propertyListing.RemoveProperties(type, location, pricing);
	}

}
