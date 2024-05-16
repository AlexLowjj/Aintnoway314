package control;

import entity.PropertyListing;

public class RemovePropertyController {
	private PropertyListing propertyListing;

	public RemovePropertyController(PropertyListing propertyListing) {
		this.propertyListing = propertyListing;
	}

	public boolean removeProperties(String type, String location, String pricing) {
		return propertyListing.removeProperties(type, location, pricing);
	}

}
