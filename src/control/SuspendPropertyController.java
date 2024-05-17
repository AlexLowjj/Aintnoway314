package control;

import entity.PropertyListing;

public class SuspendPropertyController {
	private PropertyListing propertyListing;

	public SuspendPropertyController(PropertyListing propertyListing) {
		this.propertyListing = propertyListing;
	}

	public boolean suspendProperties(String type, String location, String pricing) {
		return propertyListing.suspendProperties(type, location, pricing);
	}

}
