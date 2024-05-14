package control;

import entity.PropertyListing;

public class UpdatePropertyController {
	private PropertyListing propertyListing;

	public UpdatePropertyController(PropertyListing propertyListing) {
		this.propertyListing = propertyListing;
	}

	public String updateProperty(String type, String location, String description, String pricing, String status,
			String photo, String propertyIdHolder) {
		return propertyListing.updateProperty(type, location, description, pricing, status, photo, propertyIdHolder);
	}

}
